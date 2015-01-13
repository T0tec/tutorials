package org.t0tec.tutorials.fs;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.fs.persistence.HibernateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class FetchStrategies {

  private static final Logger logger = LoggerFactory.getLogger(FetchStrategies.class);

  public static void main(String[] args) {
    FetchStrategies fs = new FetchStrategies();
    fs.doFirstUnit();
    fs.doSecondUnit();
    fs.doThirdUnit();

    // Read about optimizing fetching for secondary tables (inheritance) at #13.2.4 (page 616)

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

      if (i % 2 == 0) {
        logger.debug("{}", i % 2);
        item.setSeller(johndoe);
        bid.setItem(item);
      } else {
        item.setSeller(suedoe);
        bid.setItem(item);
      }

      item.getBids().add(bid);

      session.save(item);
    }

    tx.commit();
    session.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    List<Item> allItems = session.createQuery("from Item").list();

    for (Item item : allItems) {
      processSeller(item);
    }

    tx.commit();
    session.close();
  }

  private void processSeller(Item item) {
    // Do something with seller of the Item object
    logger.debug("{}", item.getSeller());
  }

  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    List<Item> allItems = session.createQuery("from Item").list();

    for (Item item : allItems) {
      processBids(item);
    }

    tx.commit();
    session.close();
  }

  private void processBids(Item item) {
    // Do something with seller of the Item object
    logger.debug("{}", item.getBids());
  }

}
