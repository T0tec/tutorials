package org.t0tec.tutorials.cip;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.cip.persistence.HibernateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;

public class CachingInPractice {

  private static final Logger logger = LoggerFactory.getLogger(CachingInPractice.class);

  public static void main(String[] args) {
    CachingInPractice cip = new CachingInPractice();
    cip.doFirstUnit();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  public void doFirstUnit() {
    // First unit of work
    GregorianCalendar gc = new GregorianCalendar();
    gc.setLenient(false);
    gc.set(2015, 0, 31, 14, 33, 48);

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    for (int i = 0; i < 10; i++) {
      Item item = new Item("Item nr. " + i);

      Bid bid = new Bid(new BigDecimal(99 + i), new Date());
      Bid biggerBid = new Bid(new BigDecimal(99 + 2 * i), new Date());

      bid.setItem(item);
      biggerBid.setItem(item);

      item.getBids().add(bid);
      item.getBids().add(biggerBid);

      session.save(item);
    }

    tx.commit();
    session.close();
  }

}
