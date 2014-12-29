package org.t0tec.tutorials.ajcwf;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.ajcwf.persistence.HibernateUtil;

public class LegacyDatabases {

  private static final Logger logger = LoggerFactory.getLogger(LegacyDatabases.class);

  public static void main(String[] args) {
    LegacyDatabases ld = new LegacyDatabases();
    ld.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Item fooItem = new Item("Foo");

    Bid bidOne = new Bid(new BigDecimal(49));
    bidOne.setItem(fooItem);
    session.save(bidOne);

    Bid succesfulBid = new Bid(new BigDecimal(99));
    succesfulBid.setItem(fooItem);
    session.save(succesfulBid);

    fooItem.getBids().add(bidOne);
    fooItem.getBids().add(succesfulBid);
    fooItem.setSuccessfulBid(succesfulBid);
    session.save(fooItem);

    tx.commit();
    session.close();

    // Last unit of work
    getAllItems();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  private void getAllItems() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<Item> items = listAndCast(newSession.createQuery("from Item i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (Item i : items) {
      logger.debug(i.toString());

      for (Bid b : i.getBids()) {
        logger.debug("[{}, {}]", b.getAmount(), b.getSuccessful());
      }
    }
    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
