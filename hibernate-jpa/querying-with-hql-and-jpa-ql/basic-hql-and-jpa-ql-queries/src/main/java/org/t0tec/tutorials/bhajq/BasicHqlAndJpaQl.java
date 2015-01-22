package org.t0tec.tutorials.bhajq;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.bhajq.persistence.HibernateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BasicHqlAndJpaQl {

  private static final Logger logger = LoggerFactory.getLogger(BasicHqlAndJpaQl.class);

  public static void main(String[] args) {
    BasicHqlAndJpaQl bhajq = new BasicHqlAndJpaQl();
    bhajq.doFirstUnit();
    bhajq.doSecondUnit();
    bhajq.doThirdUnit();
    bhajq.doFourthUnit();
    bhajq.doFifthUnit();
    bhajq.doSixthUnit();
    bhajq.doSeventhUnit();
    bhajq.doEightUnit();
    bhajq.doNinthUnit();
    bhajq.doTenthUnit();
    bhajq.doTwelfthUnit();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  public void doFirstUnit() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    User suedoe = new User();
    suedoe.setFirstname("Sue");
    suedoe.setLastname("Doe");
    suedoe.setEmail("suedoe@hibernate.org");

    session.save(suedoe);

    Category laptopsCategory = new Category();
    laptopsCategory.setName("Laptops");

    Category laptopsBagsCategory = new Category();
    laptopsBagsCategory.setName("Laptop bags");

    session.save(laptopsCategory);
    session.save(laptopsBagsCategory);

    for (int i = 0; i < 10; i++) {
      Item item = new Item();
      item.setName("Item nr. " + (i + 1));
      item.setDescription("Item " + (i + 1) + " description");
      item.getCategories().add(laptopsCategory);
      laptopsCategory.getItems().add(item);
      item.setSeller(suedoe);

      session.save(item);

      Bid bid = new Bid(new BigDecimal(99.99 + new Random().nextInt(10)), new Date());
      bid.setItem(item);

      item.getBids().add(bid);

      session.save(bid);
    }

    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setFirstname("John");
      user.setLastname("Doe nr " + i);
      user.setEmail("johndoenr" + i + "@hibernate.org");
      session.save(user);
    }

    tx.commit();
    session.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // Polymorphic queries:
    //
    // from BillingDetails // returns CreditCard and BankAccount objects
    // from CreditCard
    // from Bankaccount
    // --------------------------
    // from java.io.Serializable // returns objects who implement Serializable interface
    // from java.lang.Object // returns all persistent objects
    Query query = session.createQuery("from Item item");

    List<Item> items = query.list();
    for (Item item : items) {
      logger.info(item.toString());
    }

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("from User u where u.email = 'suedoe@hibernate.org'");

    User suedoe = (User) query.uniqueResult();

    logger.info(suedoe.toString());

    tx.commit();
    session.close();
  }


  public void doFourthUnit() {
    // Fourth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query query = session.createQuery("from Item i where i.isActive = true");

    List<Item> items = query.list();
    for (Item item : items) {
      logger.info(item.toString());
    }

    tx.commit();
    session.close();
  }

  public void doFifthUnit() {
    // Fifth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // from Bid bid where bid.amount between 1 and 10
    // from User u where u.email in ('suedoe@hibernate.org', 'johndoe@hibernate.org')
    Query query = session.createQuery("from Bid bid where bid.amount > 100");

    List<Object> objects = query.list();
    for (Object object : objects) {
      logger.info(object.toString());
    }

    tx.commit();
    session.close();
  }

  public void doSixthUnit() {
    // Sixth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // from User u where u.email is not null
    // from Item i where i.successfulBid is null
    Query query = session.createQuery("from Item i where i.successfulBid is null");

    List<Object> objects = query.list();
    for (Object object : objects) {
      logger.info(object.toString());
    }

    tx.commit();
    session.close();
  }

  public void doSeventhUnit() {
    // Seventh unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // from User u where u.firstname not like '%John'
    //
    // This query returns all users with a firstname that starts with %Foo:
    // from User u where u.firstname not like '\%Foo%' escape='\'
    Query query = session.createQuery("from User u where u.firstname like 'Sue%'");

    List<Object> objects = query.list();
    for (Object object : objects) {
      logger.info(object.toString());
    }

    tx.commit();
    session.close();
  }

  public void doEightUnit() {
    // Eight unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // from User user
    // where user.firstname like 'G%' and user.lastname like 'K%'

    // from User u
    // where ( u.firstname like 'G%' and u.lastname like 'K%' )
    // or u.email in ('foo@hibernate.org', 'bar@hibernate.org' )
    Query query = session.createQuery("from Bid bid where ( bid.amount / 0.71 ) - 150.0 > 0.0");

    List<Object> objects = query.list();
    for (Object object : objects) {
      logger.info(object.toString());
    }

    tx.commit();
    session.close();
  }

  public void doNinthUnit() {
    // Ninth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // from Item i, Category c where i.id = '4' and i member of c.items
    Query query = session.createQuery("from Item i where i.bids is not empty");

    List<Object> objects = query.list();
    for (Object object : objects) {
      logger.info(object.toString());
    }

    Iterator pairs = session
        .createQuery("from Item i, Category c where i.id = 4 and i member of c.items")
        .iterate(); // returns an Item and Category Instance

    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      Item item = (Item) pair[0];
      logger.info(item.toString());
      Category category = (Category) pair[1];
      logger.info(category.toString());
    }

    tx.commit();
    session.close();
  }

  public void doTenthUnit() {
    // Tenth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // from User user where concat(user.firstname, user.lastname) like 'G% K%'
    // from Item i where size(i.bids) > 3
    // from User u order by u.username
    // from User u order by u.username desc
    // from User u order by u.lastname asc, u.firstname asc
    Query query = session.createQuery("from User u where lower(u.email) = 'suedoe@hibernate.org'");

    Object object = query.uniqueResult();

    logger.info(object.toString());

    tx.commit();
    session.close();
  }

  public void doEleventhUnit() {
    // Eleventh unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query q = session.createQuery("from Item i, Bid b");

    Iterator pairs = q.list().iterator();

    while (pairs.hasNext()) {
      Object[] pair = (Object[]) pairs.next();
      Item item = (Item) pair[0];
      Bid bid = (Bid) pair[1];
    }

    tx.commit();
    session.close();
  }

  public void doTwelfthUnit() {
    // Twelfth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // select distinct item.description from Item item
    // select item.startDate, current_date() from Item item
    // select item.startDate, item.endDate, upper(item.name) from Item item
    Query q = session.createQuery("select i.id, i.description \n"
                                  + "from Item i where i.date > current_date()");

    List<Object> list = q.list();

    for (Object o : list) {
      logger.info(o.toString());
    }

    tx.commit();
    session.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}

