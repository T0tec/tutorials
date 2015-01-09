package org.t0tec.tutorials.lelap;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.lelap.persistence.HibernateUtil;

import java.math.BigDecimal;

public class LazyEagerLoadingAndProxies {

  private static final Logger logger = LoggerFactory.getLogger(LazyEagerLoadingAndProxies.class);

  public static void main(String[] args) {
    LazyEagerLoadingAndProxies lelap = new LazyEagerLoadingAndProxies();
    lelap.doFirstUnit();
    lelap.doSecondUnit();
    lelap.doThirdUnit();
    lelap.doFourthUnit();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  public void doFirstUnit() {
    // First unit of work
    User user = new User("John", "Doe", "johndoe", "eodnhoj", "johndoe@hibernate.org", 1, true);

    Item item = new Item("Playstation 3");
    item.setDescription("Playstation 3 inc. all accessories");

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    session.save(user);
    session.save(item);

    tx.commit();
    session.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Item item = (Item) session.load(Item.class, new Long(1));
    item.getId();
    item.getDescription(); // Initialize the proxy

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // A proxy is useful if you need the Item only to create a reference, for example:
    Item item = (Item) session.load(Item.class, new Long(1));
    User user = (User) session.load(User.class, new Long(1));
    Bid newBid = new Bid(new BigDecimal(99.99));

    newBid.setItem(item);
    newBid.setBidder(user);

    session.save(newBid);

    tx.commit();
    session.close();
  }

  public void doFourthUnit() {
    // Fourth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Item item = (Item) session.load(Item.class, new Long(1));

    // Does execute a select count statment though...
    logger.debug("size() {}, isEmpty(), {}", item.getBids().size(), item.getBids().isEmpty());

    // With Fetch = eager, bids is initialized


    tx.commit();
    session.close();
  }
}
