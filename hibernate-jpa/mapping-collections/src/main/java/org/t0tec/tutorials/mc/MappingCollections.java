package org.t0tec.tutorials.mc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

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
    mc.doFirstUnit();
    mc.doSecondUnit();
    mc.doThirdUnit();
    mc.doFourthUnit();
    mc.doFifthUnit();
    mc.doSixthUnit();
    mc.doSeventhUnit();
  }

  public void doFirstUnit() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ItemS newItem = new ItemS("Set");

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

    ItemC newItem = new ItemC("Collection");

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

    ItemL newItem = new ItemL("List");

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

    ItemM newItem = new ItemM("Map");

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

  public void doFifthUnit() {
    // Fifth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ItemSM newItem = new ItemSM("Sorted Map");

    SortedMap<String, String> images = new TreeMap<String, String>();
    images.put("xyz image One", "xyz.png");
    images.put("abc image Two", "abc.png");
    images.put("def image Three", "def.png");
    // Again, duplicate elements are allowed;

    newItem.setImages(images);

    session.save(newItem);

    tx.commit();
    session.close();

    getAllItemsSM();

    HibernateUtil.shutdown();
  }

  private void getAllItemsSM() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<ItemSM> items = listAndCast(newSession.createQuery("from ItemSM i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (ItemSM item : items) {
      logger.debug(item.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  public void doSixthUnit() {
    // Sixth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ItemSS newItem = new ItemSS("SortedSet");

    SortedSet<String> images = new TreeSet<String>();
    images.add("xyz.png");
    images.add("def.png");
    images.add("abc.png"); // Set only adds unique elements

    newItem.setImages(images);

    session.save(newItem);

    tx.commit();
    session.close();

    getAllItemsSS();

    HibernateUtil.shutdown();
  }

  private void getAllItemsSS() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<ItemSS> items = listAndCast(newSession.createQuery("from ItemSS i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (ItemSS item : items) {
      logger.debug(item.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  public void doSeventhUnit() {
    // Seventh unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ItemLHM newItem = new ItemLHM("Linked Hash Map");

    LinkedHashMap<String, String> images = new LinkedHashMap<String, String>();
    images.put("xyz image One", "xyz.png");
    images.put("abc image Two", "abc.png");
    images.put("def image Three", "def.png");
    // Again, duplicate elements are allowed;

    newItem.setImages(images);

    session.save(newItem);

    tx.commit();
    session.close();

    getAllItemsLHM();

    HibernateUtil.shutdown();
  }

  private void getAllItemsLHM() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<ItemLHM> items = listAndCast(newSession.createQuery("from ItemLHM i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (ItemLHM item : items) {
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
