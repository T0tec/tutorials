package org.t0tec.tutorials.np;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.np.persistence.HibernateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchStrategies {

  private static final Logger logger = LoggerFactory.getLogger(FetchStrategies.class);

  public static void main(String[] args) {
    FetchStrategies fs = new FetchStrategies();
    fs.doFirstUnit();
    fs.doSecondUnit();
    fs.doThirdUnit();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  public void doFirstUnit() {
    // First unit of work
    GregorianCalendar gc = new GregorianCalendar();
    gc.setLenient(false);
    gc.set(2015, 0, 31, 14, 33, 48);

    Address johndoesAddress = new Address("Doe street", "90000", "Doe city");

    User johndoe = new User("John", "Doe", "johndoe", "eodnhoj", "johndoe@hibernate.org", 1, true);
    johndoe.setHomeAddress(johndoesAddress);

    User suedoe = new User("Sue", "Doe", "suedoe", "eodeus", "suedoe@hibernate.org", 2, false);

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    session.save(johndoe);
    session.save(suedoe);

    for (int i = 0; i < 10; i++) {
      Item item = new Item("Item " + i);
      Bid bid = new Bid(new BigDecimal(99 + i), new Date());
      Bid biggerBid = new Bid(new BigDecimal(99 + 2 * i), new Date());

      if (i % 2 == 0) {
        logger.debug("{}", i % 2);
        item.setSeller(johndoe);
        bid.setItem(item);
        biggerBid.setItem(item);
      } else {
        item.setSeller(suedoe);
        bid.setItem(item);
        biggerBid.setItem(item);
      }

      item.getBids().add(bid);
      item.getBids().add(biggerBid);

      session.save(item);
    }

    tx.commit();
    session.close();
  }

  // Problem:
  //  First you retrieve all Item instances; there is no difference between HQL and Cri-
  //  teria queries. This query triggers one SQL SELECT that retrieves all rows of the
  //  ITEM table and returns n persistent objects. Next, you iterate through this result
  //  and access each Item object.
  //  What you access is the bids collection of each Item . This collection isn’t initial-
  //  ized so far, the Bid objects for each item have to be loaded with an additional
  //  query. This whole code snippet therefore produces n+1 selects.
  //  You always want to avoid n+1 selects.
  // instead of: n + 1 selects: possible solutions
  //
  // 1st solution: prefetching with batches results in: n / (10 + 1)
  // 2nd solution: subselect that results in 2 queries
  // 3rd solution: prefetching with join results in a single select query
  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // List<Item> allItems = session.createQuery("from Item").list();
    // List<Item> allItems = session.createCriteria(Item.class).list();

    // List<Item> allItems =
    // session.createQuery("from Item i left join fetch i.bids")
    // .list();
    List<Item> allItems =
        session.createCriteria(Item.class)
            .setFetchMode("bids", FetchMode.JOIN)
            .list();

    Map<Item, Bid> highestBids = new HashMap<Item, Bid>();
    for (Item item : allItems) {
      Bid highestBid = null;
      for (Bid bid : item.getBids()) { // Initialize the collection
        if (highestBid == null) {
          highestBid = bid;
        }
        if (bid.getAmount().compareTo(highestBid.getAmount()) == 1) {
          highestBid = bid;
        }
      }
      highestBids.put(item, highestBid);
    }

    for (Bid b : highestBids.values()) {
      logger.debug("highest bid {}", b.toString());
    }

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    tx.commit();
    session.close();
  }

}
