package org.t0tec.tutorials.pwb;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.pwb.persistence.HibernateUtil;

import java.util.List;

public class ProcessingWithBatches {

  private static final Logger logger = LoggerFactory.getLogger(ProcessingWithBatches.class);

  private long computerId;

  public static void main(String[] args) {
    ProcessingWithBatches pwb = new ProcessingWithBatches();
    pwb.doFirstUnit();
    pwb.doSecondUnit();
    pwb.doThirdUnit();
    // Shutting down the application
    HibernateUtil.shutdown();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }

  public void doFirstUnit() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    for (int i = 0; i < 100000; i++) {
      Item item = new Item("Item " + i);
      session.save(item);
      if (i % 100 == 0) {
        session.flush();
        session.clear();
      }
    }

    tx.commit();
    session.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ScrollableResults itemCursor =
        session.createQuery("from Item").scroll();
    int count = 0;
    while (itemCursor.next()) {
      Item item = (Item) itemCursor.get(0);
      modifyItem(item, true);
      if (++count % 100 == 0) {
        session.flush();
        session.clear();
      }
    }

    tx.commit();
    session.close();
  }

  private void modifyItem(Item item, boolean isActive) {
    item.setActive(isActive);
  }

  //  The batching is gone in this example—you open a StatelessSession . You no
  //  longer work with objects in persistent state; everything that is returned from the
  //  database is in detached state. Hence, after modifying an Item object, you need to
  //  call update() to make your changes permanent. Note that this call no longer reat-
  //  taches the detached and modified item . It executes an immediate SQL UPDATE ;
  //  the item is again in detached state after the command.
  //  Disabling the persistence context and working with the StatelessSession
  //  interface has some other serious consequences and conceptual limitations
  public void doThirdUnit() {
    // Third unit of work
    StatelessSession session = HibernateUtil.getSessionFactory().openStatelessSession();
    Transaction tx = session.beginTransaction();

    ScrollableResults itemCursor =
        session.createQuery("from Item").scroll();
    while (itemCursor.next()) {
      Item item = (Item) itemCursor.get(0);
      modifyItem(item, false);
      session.update(item);
    }

    tx.commit();
    session.close();
  }

}
