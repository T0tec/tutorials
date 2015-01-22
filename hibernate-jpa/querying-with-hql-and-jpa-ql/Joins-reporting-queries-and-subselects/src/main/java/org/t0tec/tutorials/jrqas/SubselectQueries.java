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

public class SubselectQueries {

  private static final Logger logger = LoggerFactory.getLogger(SubselectQueries.class);

  public static void main(String[] args) {
    SubselectQueries sq = new SubselectQueries();
    sq.doFirstUnit();
    sq.doSecondUnit();
    sq.doThirdUnit();;

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

  // Correlated and uncorrelated nesting
  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("from User u where 1 < (\n"
                                      + "select count(i) from u.itemsForSale i where i.successfulBid is not null\n"
                                      + ")");

    List<User> users = (List<User>) query.list();

    for (User user : users) {
      logger.info("user: {}", user.toString());
    }

    //  uncorrelated query
    query = session.createQuery("from Bid bid where bid.amount + 1 >= (\n"
                                + "select max(b.amount) from Bid b\n"
                                + ")");

    Iterator pairs = query.iterate();

    while (pairs.hasNext()) {
      Bid bid = (Bid) pairs.next();
      logger.info("Bid: {} ", bid);
    }

    tx.commit();
    session.close();
  }

  // Quantification
  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // All items with a bid of exactly 100
    // from Item i where 100 = some (select b.amount from i.bids b)
    // from Item i where 100 in (select b.amount from i.bids b)
    // All items where bids are greather than 100
    // from Item i where 100 <= any (select b.amount from i.bids b)
    // All items where bids are lower than 100
    Query query =
        session.createQuery("from Item i where 100 > all (select b.amount from i.bids b)");

    List<Item> items = (List<Item>) query.list();

    for (Item item : items) {
      logger.info("item: {}", item.toString());
    }

    // The query returns all categories to which the item belongs
    Item item = items.get(0);

    List<Category> result =
        session.createQuery("from Category c" +
                            " where :givenItem in elements(c.items)")
            .setEntity("givenItem", item)
            .list();

//    List<Category> result =
//        session.createQuery(
//            "from Category c where :givenItem in (select i from c.items i)"
//        )
//            .setEntity("item", item)
//            .list();

    for (Category category : result) {
      logger.info("category: {}", category.toString());
    }

    tx.commit();
    session.close();
  }

}

