package org.t0tec.tutorials.pbr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.pbr.persistence.HibernateUtil;

public class PersistenceByReachability {

  private static final Logger logger = LoggerFactory.getLogger(PersistenceByReachability.class);

  private long computerId;

  public static void main(String[] args) {
    PersistenceByReachability pbr = new PersistenceByReachability();
    pbr.doFirstUnit();
    // pbr.doSecondUnit();
    // pbr.doThirdUnit();
    pbr.doFourthUnit();
    pbr.getAllCategories();

    // pbr.doFifthUnit();
    // pbr.getAllItems();
    // Shutting down the application
    HibernateUtil.shutdown();
  }


  public void doFirstUnit() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Category computer = new Category("Computer");

    computerId = (Long) session.save(computer);

    tx.commit();
    session.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Category computer = (Category) session.load(Category.class, computerId); // proxy
    Category laptops = new Category("Laptops");
    computer.getChildCategories().add(laptops);
    laptops.setParentCategory(computer);

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    // Same as second unit but create the link outside the persistence context
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Category computer = (Category) session.get(Category.class, computerId); // no proxy

    tx.commit();
    session.close();

    Category laptops = new Category("Laptops");
    // Because you have cascade="none" defined for the parentCategory association,
    // Hibernate ignores changes to any of the other categories in the hierarchy
    // (Computer, Electronics)! It doesn’t cascade the call to save() to entities referred
    // by this association. If you enabled cascade="save-update" on the <many-to-one>
    // mapping of parentCategory, Hibernate would navigate the whole graph of
    // objects in memory, synchronizing all instances with the database. This is an obvious
    // overhead you’d prefer to avoid.
    // In this case, you neither need nor want transitive persistence for the parent-
    // Category association
    computer.getChildCategories().add(laptops);
    laptops.setParentCategory(computer);

    Session secondSession = HibernateUtil.getSessionFactory().openSession();
    Transaction secondTx = secondSession.beginTransaction();

    // Persist one new category and the link to its parent category
    secondSession.save(computer);

    secondTx.commit();
    secondSession.close();
  }

  public void doFourthUnit() {
    // Fourth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Category computer = (Category) session.get(Category.class, computerId); // no proxy

    Hibernate.initialize(computer.getChildCategories()); // Solve LazyInitializationException

    tx.commit();
    session.close();

    Category laptops = new Category("Laptops");

    Category laptopUltraPortable = new Category("Ultra-Portable");
    Category laptopTabletPCs = new Category("Tablet PCs");
    laptops.addChildCategory(laptopUltraPortable);
    laptops.addChildCategory(laptopTabletPCs);

    computer.addChildCategory(laptops);

    Session secondSession = HibernateUtil.getSessionFactory().openSession();
    Transaction secondTx = secondSession.beginTransaction();

    secondSession.save(laptops);

    secondTx.commit();
    secondSession.close();

    laptops.setName("Laptop Computers"); // Modify
    laptopUltraPortable.setName("Ultra-Portable Notebooks"); // Modify
    laptopTabletPCs.setName("Tablet Computers"); // Modify
    Category laptopBags = new Category("Laptop Bags");
    laptops.addChildCategory(laptopBags); // Add

    Session thirdSession = HibernateUtil.getSessionFactory().openSession();
    Transaction thirdTx = thirdSession.beginTransaction();

    thirdSession.saveOrUpdate(laptops);

    thirdTx.commit();
    thirdSession.close();
  }

  public void doFifthUnit() {
    // Fifth unit of work
    Item item = new Item("Foo item");
    Bid aBid = new Bid(new BigDecimal(100), new Date());
    Bid anotherBid = new Bid(new BigDecimal(110), new Date());
    Bid lastBid = new Bid(new BigDecimal(120), new Date());
    item.addBid(aBid);
    item.addBid(anotherBid);
    item.addBid(lastBid);

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    long itemId = (Long) session.save(item);

    Item anItem = (Item) session.get(Item.class, itemId);

    tx.commit();
    session.close();

    anItem.getBids().remove(aBid);
    anItem.getBids().remove(anotherBid);

    Session secondSession = HibernateUtil.getSessionFactory().openSession();
    Transaction secondTx = secondSession.beginTransaction();

    secondSession.saveOrUpdate(anItem);

    secondTx.commit();
    secondSession.close();
  }

  private void getAllCategories() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<Category> categories =
        listAndCast(newSession.createQuery("from Category c order by c.id asc"));
    logger.debug("{} category(ies) found", categories.size());
    for (Category i : categories) {
      logger.debug(i.toString());
    }
    newTransaction.commit();
    newSession.close();
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
