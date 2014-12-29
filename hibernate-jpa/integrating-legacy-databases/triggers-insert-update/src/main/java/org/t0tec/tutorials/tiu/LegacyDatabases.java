package org.t0tec.tutorials.tiu;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.tiu.persistence.HibernateUtil;

public class LegacyDatabases {

  private static final Logger logger = LoggerFactory.getLogger(LegacyDatabases.class);

  public static void main(String[] args) {
    LegacyDatabases ld = new LegacyDatabases();
    ld.doWork();
  }

  public void doWork() {
    // First unit of work
    Item item = new Item("item");

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    session.save(item);
    session.flush(); // Force the INSERT to occur
    session.refresh(item); // Reload the object with a SELECT
    logger.debug("created: {}, version: {}", item.getCreated(), item.getVersion());

    try {
      Thread.sleep(2000); // wait 2 seconds
      logger.debug("Sleeping 2 seconds");
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }

    session.evict(item);

    item.setName("Item Foo");

    session.saveOrUpdate(item);
    session.flush();
    session.refresh(item);
    logger.debug("last modified: {}, version: {}", item.getLastModified(), item.getVersion());

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
