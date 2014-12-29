package org.t0tec.tutorials.ijp;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.ijp.persistence.HibernateUtil;

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

    DailyBilling db = new DailyBilling(1, new BigDecimal(49));
    db.setItem(fooItem);

    session.save(db);

    fooItem.setBillingTotal(db.getTotal());

    session.save(fooItem);

    tx.commit();
    session.close();

    // Second unit of work
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
    }
    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
