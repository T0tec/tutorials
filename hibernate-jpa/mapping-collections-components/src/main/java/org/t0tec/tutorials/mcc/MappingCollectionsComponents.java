package org.t0tec.tutorials.mcc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.mcc.persistence.HibernateUtil;

public class MappingCollectionsComponents {

  private static final Logger logger = LoggerFactory.getLogger(MappingCollectionsComponents.class);

  public static void main(String[] args) {
    MappingCollectionsComponents mcc = new MappingCollectionsComponents();
    mcc.doFirstUnit();
    mcc.doSecondUnit();
  }

  public void doFirstUnit() {
    // First unit of work: Item with a set of images that doesn't allow null values & duplicate
    // Images
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ItemS item = new ItemS("Set");
    HashSet<ImageS> images = new HashSet<ImageS>();
    images.add(new ImageS("Foo Image 1", "foo1.png", 125, 125));
    images.add(new ImageS("Foo Image 2", "foo2.png", 400, 200));
    images.add(new ImageS("Foo Image 3", "foo3.png", 50, 50));
    images.add(new ImageS("Foo Image 3", "foo3.png", 50, 50)); // no duplicates allowed

    item.setImages(images);

    session.persist(item);

    tx.commit();
    session.close();

    getAllItemSs();

    HibernateUtil.shutdown();
  }

  private void getAllItemSs() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<ItemS> items = listAndCast(newSession.createQuery("from ItemS i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (ItemS item : items) {
      logger.debug(item.toString());
      if (!item.getImages().isEmpty()) {
        for (ImageS i : item.getImages()) {
          logger.debug("{}, size: {}x{}, parent: {}", i.getName(), i.getSizeX(), i.getSizeY(), i
              .getItemS().getName());
          // you can access bidirectional from Image(child) to Item(parent)
        }
      }
    }

    newTransaction.commit();
    newSession.close();
  }

  public void doSecondUnit() {
    // Second unit of work: Item with
    // a Collection of images that does allow null values & duplicate Images
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    ItemC item = new ItemC("Collection");
    Collection<ImageC> images = new ArrayList<ImageC>();
    images.add(new ImageC("Bar Image 1", "bar1.png", 125, 125));
    images.add(new ImageC("Bar Image 2", "bar2.png", 400, 200));
    images.add(new ImageC("Bar Image 3", "bar3.png", 50, 50));
    images.add(new ImageC("Bar Image 3", "bar3.png", 50, 50));
    images.add(new ImageC("Bar image 4", "bar4.png", null, null));
    item.setImages(images);

    session.persist(item);

    tx.commit();
    session.close();

    getAllItemCs();

    HibernateUtil.shutdown();
  }

  private void getAllItemCs() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<ItemC> items = listAndCast(newSession.createQuery("from ItemC i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (ItemC item : items) {
      logger.debug(item.toString());
      if (!item.getImages().isEmpty()) {
        for (ImageC i : item.getImages()) {
          logger.debug("{}, size: {}x{}, parent: {}", i.getName(), i.getSizeX(), i.getSizeY(), i
              .getItemC().getName());
          // you can access bidirectional from Image(child) to Item(parent)
        }
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
