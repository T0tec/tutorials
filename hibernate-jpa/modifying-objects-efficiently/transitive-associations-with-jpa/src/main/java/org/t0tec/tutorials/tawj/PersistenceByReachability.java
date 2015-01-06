package org.t0tec.tutorials.tawj;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceByReachability {

  private static final Logger logger = LoggerFactory.getLogger(PersistenceByReachability.class);

  private static EntityManagerFactory emf;

  private long computerId = 1L;

  public static void main(String[] args) {
    // Start EntityManagerFactory
    emf = Persistence.createEntityManagerFactory("jpa");
    PersistenceByReachability pbr = new PersistenceByReachability();
    pbr.doFirstUnit();
    // pbr.doSecondUnit();
    // pbr.doThirdUnit();
    pbr.doFourthUnit();
    pbr.getAllCategories();

    // pbr.doFifthUnit();
    // pbr.getAllItems();
    // Shutting down the application
    emf.close();
  }


  public void doFirstUnit() {
    // First unit of work
    Category computer = new Category("Computer");

    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    em.persist(computer);

    tx.commit();
    em.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Category computer = (Category) em.getReference(Category.class, computerId); // proxy
    Category laptops = new Category("Laptops");
    computer.getChildCategories().add(laptops);
    laptops.setParentCategory(computer);

    tx.commit();
    em.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    // Same as second unit but create the link outside the persistence context
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Category computer = (Category) em.find(Category.class, computerId); // no proxy

    Hibernate.initialize(computer.getChildCategories());

    tx.commit();
    em.close();

    Category laptops = new Category("Laptops");
    computer.getChildCategories().add(laptops);
    laptops.setParentCategory(computer);

    EntityManager secondEm = emf.createEntityManager();
    EntityTransaction secondTx = secondEm.getTransaction();
    secondTx.begin();

    secondEm.merge(computer);

    secondTx.commit();
    secondEm.close();
  }

  public void doFourthUnit() {
    // Fourth unit of work
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Category computer = (Category) em.find(Category.class, computerId); // no proxy

    Hibernate.initialize(computer.getChildCategories()); // Solve LazyInitializationException

    tx.commit();
    em.close();

    Category laptops = new Category("Laptops");

    Category laptopUltraPortable = new Category("Ultra-Portable");
    Category laptopTabletPCs = new Category("Tablet PCs");
    laptops.addChildCategory(laptopUltraPortable);
    laptops.addChildCategory(laptopTabletPCs);

    computer.addChildCategory(laptops);

    EntityManager secondEm = emf.createEntityManager();
    EntityTransaction secondTx = secondEm.getTransaction();
    secondTx.begin();

    secondEm.persist(laptops);

    secondTx.commit();
    secondEm.close();

    laptops.setName("Laptop Computers"); // Modify
    laptopUltraPortable.setName("Ultra-Portable Notebooks"); // Modify
    laptopTabletPCs.setName("Tablet Computers"); // Modify
    Category laptopBags = new Category("Laptop Bags");
    laptops.addChildCategory(laptopBags); // Add

    EntityManager thirdEm = emf.createEntityManager();
    EntityTransaction thirdTx = thirdEm.getTransaction();
    thirdTx.begin();

    thirdEm.merge(laptops);

    thirdTx.commit();
    thirdEm.close();
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

    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    em.persist(item);

    Item anItem = em.find(Item.class, 1L);

    tx.commit();
    em.close();

    anItem.getBids().remove(aBid);
    anItem.getBids().remove(anotherBid);

    EntityManager secondEm = emf.createEntityManager();
    EntityTransaction secondTx = secondEm.getTransaction();
    secondTx.begin();

    secondEm.merge(anItem);

    secondTx.commit();
    secondEm.close();
  }

  private void getAllCategories() throws HibernateException {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    List<Category> categories = listAndCast(em.createQuery("from Category c order by c.id asc"));
    logger.debug("{} category(ies) found", categories.size());
    for (Category i : categories) {
      logger.debug(i.toString());
    }
    tx.commit();
    em.close();
  }


  private void getAllItems() throws HibernateException {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    List<Item> items = listAndCast(em.createQuery("from Item i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (Item i : items) {
      logger.debug(i.toString());
    }
    tx.commit();
    em.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(javax.persistence.Query query) {
    return query.getResultList();
  }
}
