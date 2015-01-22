package org.t0tec.tutorials.jrqas;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.jrqas.persistence.HibernateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class JoinQueries {

  private static final Logger logger = LoggerFactory.getLogger(JoinQueries.class);

  public static void main(String[] args) {
    JoinQueries jq = new JoinQueries();
    jq.doFirstUnit();
    jq.doSecondUnit();
    jq.doThirdUnit();
    jq.doFourthUnit();
    jq.doFifthUnit();
    jq.doSixthUnit();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  public void doFirstUnit() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    User suedoe = new User();
    suedoe.setUsername("suedoe");
    suedoe.setFirstname("Sue");
    suedoe.setLastname("Doe");
    suedoe.setEmail("suedoe@hibernate.org");

    session.save(suedoe);

    Category laptopsCategory = new Category();
    laptopsCategory.setName("Laptops");

    Category laptopsBagsCategory = new Category();
    laptopsBagsCategory.setName("Laptop bags");

    session.save(laptopsCategory);
    session.save(laptopsBagsCategory);

    for (int i = 0; i < 10; i++) {
      Item item = new Item();
      item.setName("Item nr. " + (i + 1));
      item.setDescription("Item " + (i + 1) + " description");
      item.getCategories().add(laptopsCategory);
      laptopsCategory.getItems().add(item);
      item.setSeller(suedoe);

      session.save(item);

      Bid bid = new Bid(new BigDecimal(97.99 + new Random().nextInt(10)), new Date());
      bid.setItem(item);
      bid.setBidder(suedoe);

      if (i % 2 == 0) {
        Bid possibleBid = new Bid(new BigDecimal(97.99 + new Random().nextInt(10)), new Date());
        possibleBid.setItem(item);
        possibleBid.setBidder(suedoe);
        item.getBids().add(possibleBid);
        session.save(possibleBid);
      }

      item.getBids().add(bid);

      session.save(bid);
    }

    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setFirstname("John");
      user.setLastname("Doe nr " + i);
      user.setEmail("johndoenr" + i + "@hibernate.org");
      Address address = new Address("Doe street nr. " + i, "90000", "Doe city");
      user.setHomeAddress(address);
      session.save(user);
    }

    tx.commit();
    session.close();
  }

  // Implicit association joins
  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // select distinct u.homeAddress.city from User u // This query returns a List of Strings
    // from Bid bid where bid.item.description like '%Item%'
    //
    // from Bid bid where bid.item.category.name like 'Laptop%' // if category is many-to-one
    // from Bid bid
    // where bid.item.category.name like 'Laptop%'
    // and bid.item.successfulBid.amount > 100
    Query query = session.createQuery("from User u where u.homeAddress.city = 'Doe city'");

    List<Object> objects = query.list();
    for (Object o : objects) {
      logger.info(o.toString());
    }

    tx.commit();
    session.close();
  }

  // Joins expressed in the FROM clause
  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // from Item i join i.bids b
    Query query = session.createQuery("from Item i\n"
                                      + "join i.bids b\n"
                                      + "where i.description like '%Item%'\n"
                                      + "and b.amount > 100");

    Iterator pairs = query.list().iterator();
    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      Item item = (Item) pair[0];
      Bid bid = (Bid) pair[1];
      logger.info(item.toString());
      logger.info(bid.toString());
    }

    // select i from Item i join i.bids b == from Item i in(i.bids)
    query = session.createQuery("select i from Item i join i.bids b");
    Iterator items = query.list().iterator();
    while (items.hasNext()) {
      Item item = (Item) items.next();
      logger.info(item.toString());
    }

    query = session.createQuery("from Item i\n"
                                      + "left join i.bids b\n"
                                      + "with b.amount > 100\n"
                                      + "where i.description like 'Item%'");

    pairs = query.list().iterator();
    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      Item item = (Item) pair[0];
      Bid bid = (Bid) pair[1];
    }

    tx.commit();
    session.close();
  }

  // Dynamic fetching strategies with joins
  public void doFourthUnit() {
    // Fourth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // from Item i
    // left join fetch i.bids
    // where i.description like 'Item%'
    Query query = session.createQuery("from Bid bid\n"
                                      + "left join fetch bid.item\n"
                                      + "left join fetch bid.bidder\n"
                                      + "where bid.amount > 100");

    List<Object> objects = query.list();
    for (Object o : objects) {
      logger.info(o.toString());
    }

    tx.commit();
    session.close();
  }

  // Theta-style joins
  public void doFifthUnit() {
    // Fifth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // from User user, LogRecord log where user.username = log.username
    Query query = session.createQuery("from User, Category");

    Iterator pairs = query.list().iterator();
    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      User user = (User) pair[0];
      Category category = (Category) pair[1];
    }

    tx.commit();
    session.close();
  }

  // Comparing identifiers
  public void doSixthUnit() {
    // Sixth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // from Item i join i.seller u
    // where u.username = 'steve'
    // from Item i, Bid b
    // where i.seller = b.bidder
    // from Bid b where b.item.id = 1 // no joins!
    // from Bid b where b.item.description like '%Foo%' // implicit table join
    Query query = session.createQuery("from Item i, User u\n"
                                      + "where i.seller = u and u.username = 'suedoe'");

    Iterator pairs = query.list().iterator();
    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      Item item = (Item) pair[0];
      User user = (User) pair[1];
      logger.info(item.toString());
      logger.info(user.toString());
    }

    tx.commit();
    session.close();
  }


  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}

