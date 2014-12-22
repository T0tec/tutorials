package org.t0tec.tutorials.mc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.mc.persistence.HibernateUtil;

public class MappingCollections {

  private static final Logger logger = LoggerFactory.getLogger(MappingCollections.class);

  public static void main(String[] args) {
    MappingCollections mc = new MappingCollections();
    // mc.doFirstUnit();
    // mc.doSecondUnit();
    // mc.doThirdUnit();
    mc.doFourthUnit();
  }

  public void doFirstUnit() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ItemS newItem = new ItemS("Foo");

    Set<String> images = new HashSet<String>();
    images.add("foo1.png");
    images.add("foo2.png");
    images.add("foo2.png"); // Set only adds unique elements

    newItem.setImages(images);

    session.save(newItem);

    tx.commit();
    session.close();

    getAllItemsS();

    HibernateUtil.shutdown();
  }

  private void getAllItemsS() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<ItemS> items = listAndCast(newSession.createQuery("from ItemS i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (ItemS item : items) {
      logger.debug(item.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ItemC newItem = new ItemC("Bar");

    ArrayList<String> images = new ArrayList<String>();
    images.add("bar1.png");
    images.add("bar1.png");
    images.add("bar2.png"); // ArrayList does add duplicate elements

    newItem.setImages(images);

    session.save(newItem);

    tx.commit();
    session.close();

    getAllItemsC();

    HibernateUtil.shutdown();
  }

  private void getAllItemsC() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<ItemC> items = listAndCast(newSession.createQuery("from ItemC i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (ItemC item : items) {
      logger.debug(item.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ItemL newItem = new ItemL("Lee");

    ArrayList<String> images = new ArrayList<String>();
    images.add("lee1.png");
    images.add("lee1.png");
    images.add("lee2.png"); // ArrayList does add duplicate elements

    newItem.setImages(images);

    session.save(newItem);

    tx.commit();
    session.close();

    getAllItemsL();

    HibernateUtil.shutdown();
  }

  private void getAllItemsL() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<ItemL> items = listAndCast(newSession.createQuery("from ItemL i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (ItemL item : items) {
      logger.debug(item.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  public void doFourthUnit() {
    // Fourth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ItemM newItem = new ItemM("Moo");

    Map<String, String> images = new HashMap<String, String>();
    images.put("Moo image One", "moo1.png");
    images.put("Moo image Two", "moo2.png");
    images.put("Moo image Three", "moo3.png");
    // Again, duplicate elements are allowed;

    newItem.setImages(images);

    session.save(newItem);

    tx.commit();
    session.close();

    getAllItemsM();

    HibernateUtil.shutdown();
  }

  private void getAllItemsM() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<ItemM> items = listAndCast(newSession.createQuery("from ItemM i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (ItemM item : items) {
      logger.debug(item.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
