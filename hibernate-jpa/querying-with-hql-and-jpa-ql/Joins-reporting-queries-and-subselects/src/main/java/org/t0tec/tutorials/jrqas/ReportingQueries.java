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

public class ReportingQueries {

  private static final Logger logger = LoggerFactory.getLogger(ReportingQueries.class);

  public static void main(String[] args) {
    ReportingQueries rq = new ReportingQueries();
    rq.doFirstUnit();
    rq.doSecondUnit();
    rq.doThirdUnit();
    rq.doFourthUnit();
    rq.doFifthUnit();

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

    User johndoe = new User();
    johndoe.setUsername("johndoe");
    johndoe.setFirstname("John");
    johndoe.setLastname("Doe");
    johndoe.setEmail("johndoe@hibernate.org");

    session.save(suedoe);
    session.save(johndoe);

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
        item.setSuccessfulBid(possibleBid);
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

  // Projection with aggregation functions
  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // select count(i) frim Item i
    // select count(i.successfulBid) from Item i
    // select count(distinct i.description) from Item i
    Query query = session.createQuery("select count(i.successfulBid) from Item i");
    Long count = (Long) query.uniqueResult();
    logger.info("Items count: {}", count);

    // select min(bid.amount), max(bid.amount)
    // from Bid bid where bid.item.id = 1
    query = session.createQuery("select sum(i.successfulBid.amount) from Item i");
    BigDecimal totalAmount = (BigDecimal) query.uniqueResult();
    logger.info("Total amount: {}", totalAmount);

    query = session.createQuery("select min(bid.amount), max(bid.amount)\n"
                                + "from Bid bid where bid.item.id = 1");

    Iterator pairs = query.iterate();

    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      BigDecimal minBid = (BigDecimal)pair[0];
      BigDecimal maxBid = (BigDecimal)pair[1];
      logger.info("max bid: {}, min bid: {} ", maxBid, minBid);
    }

    tx.commit();
    session.close();
  }

  // Grouping aggregated results
  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("select u.lastname, count(u) from User u\n"
                                      + "group by u.lastname");
    Iterator pairs = query.iterate();

    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      String lastname = (String) pair[0];
      Long count = (Long) pair[1];
      logger.info("{lastname: {}, count: {} } ", lastname, count);
    }

    query = session.createQuery("select bid.item.id, avg(bid.amount) from Bid bid\n"
                                     + "group by bid.item.id");

    pairs = query.iterate();

    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      Long itemId = (Long) pair[0];
      Double averageAmount = (Double) pair[1];
      logger.info("{item id: {}, avg bid amount: {} } ", itemId, averageAmount);
    }

    // select bidItem.id, count(bid), avg(bid.amount)
    // from Bid bid
    // join bid.item bidItem
    // where bidItem.successfulBid is null
    // group by bidItem.id
    query = session.createQuery("select bid.item.id, count(bid), avg(bid.amount)\n"
                                + "from Bid bid\n"
                                + "where bid.item.successfulBid is not null\n"
                                + "group by bid.item.id");

    pairs = query.iterate();

    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      Long itemId = (Long) pair[0];
      Long count = (Long) pair[1];
      Double averageAmount = (Double) pair[2];
      logger.info("{item id: {}, count: {}, avg bid amount: {} } ", itemId, count, averageAmount);
    }

    tx.commit();
    session.close();
  }

  // Restricting groups with having
  public void doFourthUnit() {
    // Fourth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("select user.lastname, count(user)\n"
                                      + "from User user\n"
                                      + "group by user.lastname\n"
                                      + "having user.lastname like 'Doe'");
    Iterator pairs = query.iterate();

    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      String lastname = (String) pair[0];
      Long count = (Long) pair[1];
      logger.info("{lastname: {}, count: {} } ", lastname, count);
    }

    query = session.createQuery("select item.id, count(bid), avg(bid.amount)\n"
                                + "from Item item\n"
                                + "join item.bids bid\n"
                                + "where item.successfulBid is not null\n"
                                + "group by item.id\n"
                                + "having count(bid) > 1");
    pairs = query.iterate();

    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      Long itemId = (Long) pair[0];
      Long count = (Long) pair[1];
      Double averageAmount = (Double) pair[2];
      logger.info("{item id: {}, count: {}, avg bid amount: {} } ", itemId, count, averageAmount);
    }


    tx.commit();
    session.close();
  }

  // Utilizing dynamic instantiation
  public void doFifthUnit() {
    // Fifth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("select new ItemBidSummary(\n"
                                      + "bid.item.id, count(bid), avg(bid.amount))\n"
                                      + "from Bid bid\n"
                                      + "where bid.item.successfulBid is null\n"
                                      + "group by bid.item.id");
    Iterator pairs = query.iterate();

    while (pairs.hasNext()) {
      ItemBidSummary itemBidSummary = (ItemBidSummary) pairs.next();
      logger.info(itemBidSummary.toString());
    }

    tx.commit();
    session.close();
  }

  // Improving performance with report queries ==> read page 694
}

