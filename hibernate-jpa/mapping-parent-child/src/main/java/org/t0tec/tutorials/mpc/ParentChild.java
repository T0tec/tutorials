package org.t0tec.tutorials.mpc;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.mpc.persistence.HibernateUtil;

public class ParentChild {

  private static final Logger logger = LoggerFactory.getLogger(ParentChild.class);

  // http://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby
  public static void main(String[] args) {
    ParentChild pc = new ParentChild();
    pc.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Item item = new Item("Foo");
    // no changes are made persistent + gives an error
    // item.getBids().add(new Bid(new BigDecimal(399), GregorianCalendar.getInstance().getTime()));
    // Set both sides of the association

    Bid aBid = new Bid(new BigDecimal(549), GregorianCalendar.getInstance().getTime());
    item.addBid(aBid);

    item.addBid(new Bid(new BigDecimal(499), GregorianCalendar.getInstance().getTime()));

    session.save(item);

    // without org.hibernate.annotations.CascadeType.DELETE_ORPHAN won't work
    // org.hibernate.annotations.CascadeType.DELETE_ORPHAN == @OneToMany(mappedBy = "item",
    // orphanRemoval = true)
    item.getBids().remove(aBid);


    Item newItem = new Item("Bar");
    newItem.addBid(new Bid(new BigDecimal(99), GregorianCalendar.getInstance().getTime()));

    session.save(newItem);

    // won't work without org.hibernate.annotations.CascadeType.REMOVE
    session.delete(newItem);

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
    for (Item item : items) {
      logger.debug(item.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  private void getAllBids() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<Bid> bids = listAndCast(newSession.createQuery("from Bid b order by b.id asc"));
    logger.debug("{} bid(s) found", bids.size());
    for (Bid b : bids) {
      logger.debug(b.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
