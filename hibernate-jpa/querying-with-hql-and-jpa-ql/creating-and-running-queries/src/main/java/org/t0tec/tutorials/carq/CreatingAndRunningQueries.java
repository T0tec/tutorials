package org.t0tec.tutorials.carq;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.FlushModeType;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.carq.persistence.HibernateUtil;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.soap.SOAPBinding;
import javax.persistence.criteria.Order;

public class CreatingAndRunningQueries {

  private static final Logger logger = LoggerFactory.getLogger(CreatingAndRunningQueries.class);

  public static void main(String[] args) {
    CreatingAndRunningQueries carq = new CreatingAndRunningQueries();
    carq.doFirstUnit();

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
      item.setName("Item nr. " + i);
      item.setDescription("Item " + i + " description");
      item.getCategories().add(laptopsCategory);
      laptopsCategory.getItems().add(item);
      item.setSeller(suedoe);

      session.save(item);
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

    // Hibernate Query Language (HQL)
    session.createQuery("from Category c where c.name like 'Laptop%'");

    // The subset standardized as JPA QL
    // entityManager.createQuery("select c from Category c where c.name like 'Laptop%'");

    // Criteria API for query by criteria (QBC) and query by example (QBE):
    session.createCriteria(Category.class).add(Restrictions.like("name", "Laptop%"));

    // Direct SQL with or without automatic mapping of resultsets to objects:
    session.createSQLQuery("select {c.*} from CATEGORY {c} where NAME like 'Laptop%'")
        .addEntity("c", Category.class);

    Query hqlQuery = session.createQuery("from User");

    // createSQLQuery() is used to create an SQL query using the native syntax of the
    // underlying database:
    Query
        sqlQuery =
        session.createSQLQuery("select {user.*} from USERS {user}").addEntity("user", User.class);

    Criteria crit = session.createCriteria(User.class);

    // To create a javax.persistence.Query instance for JPA QL , call createQuery() :
    // Query ejbQuery = em.createQuery("select u from User u");

    // To create a native SQL query, use createNativeQuery() :
    // Query sqlQuery =
    // em.createNativeQuery("select u.USER_ID, u.FIRSTNAME, u.LASTNAME from USERS u", User.class);

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    //----------
    // Paging
    //---------
    Query query = session.createQuery("from User u order by u.name asc");
    query.setMaxResults(10);

    Criteria crit = session.createCriteria(User.class);
    crit.addOrder(org.hibernate.criterion.Order.asc("name"));
    crit.setFirstResult(40);
    crit.setMaxResults(20);


    Query sqlQuery = session.createSQLQuery("select {u.*} from USERS {u}").addEntity("u",
                                                                                     User.class);
    sqlQuery.setFirstResult(40);
    sqlQuery.setMaxResults(20);

    // Same as above; only with method-chaining
    //    Query query =
    //        session.createQuery("from User u order by u.name asc")
    //            .setMaxResults(10);
    //    Criteria crit =
    //        session.createCriteria(User.class)
    //            .addOrder( Order.asc("name") )
    //            .setFirstResult(40)
    //            .setMaxResults(20);



    tx.commit();
    session.close();
  }


  public void doFourthUnit() {
    // Fourth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    //----------
    // Parameter binding
    //---------

    // NEVER DO THIS: POSSIBLE SQL INJECTION POSSIBLE!!!!
    //    String search = "foo' and callSomeStoredProcedure() and 'bar' = 'bar";
    //    String queryString = "from Item i where i.description like '" + search + "'";

    // Better:
    String queryString = "from Item item where item.description like :search";

    String searchString = "Playstation";

    Query q = session.createQuery(queryString).setString("search", searchString);

    queryString = "from Item item"
                         + " where item.description like :search"
                         + " and item.date > :minDate";

    q = session.createQuery(queryString)
        .setString("search", searchString)
        .setDate("minDate", new Date());

    // JPA
    //    Query q = em.createQuery(queryString)
    //        .setParameter("search", searchString)
    //        .setParameter("minDate", mDate, TemporalType.DATE);

    tx.commit();
    session.close();
  }

  public void doFifthUnit() {
    // Fifth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    //----------
    // Hibernate Parameter binding
    //---------

    User theSeller = (User) session.get(User.class, 1L);

    session.createQuery("from Item item where item.seller = :seller")
        .setEntity("seller", theSeller);

    String queryString = "from Item item"
                         + " where item.seller = :seller and"
                         + " item.description like :desc";

    String description = "Playstation";
    session.createQuery(queryString)
        .setParameter("seller",
                      theSeller)
        .setParameter("desc", description);

    //    MonetaryAmount givenAmount = new MonetaryAmount();
    //    Query q = session.createQuery("from Bid where amount > :amount");
    //    q.setParameter( "amount", givenAmount);

    //    Item item = new Item();
    //    item.setSeller(seller);
    //    item.setDescription(description);
    //    String queryString = "from Item item"
    //                         + " where item.seller = :seller and"
    //                         + " item.description like :desccription";
    //    session.createQuery(queryString).setProperties(item);


    session.createQuery("from User as u where u.username = :name").setString("name", null);

    // However, the result of this code is almost certainly not what you intended! The
    // resulting SQL will contain a comparison like USERNAME = null , which always eval-
    // uates to null in SQL ternary logic. Instead, you must use the is null operator:
    session.createQuery("from User as u where u.username is null");

    tx.commit();
    session.close();
  }

  public void doSixthUnit() {
    // Sixth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    //----------
    // Postitional Parameter binding
    //---------

    String searchString = "Playstation";
    Date minDate = GregorianCalendar.getInstance().getTime();

    String queryString = "from Item item"
                         + " where item.description like ?"
                         + " and item.date > ?";
    Query q = session.createQuery(queryString)
        .setString(0, searchString)
        .setDate(1, minDate);

    // JPA:
    //    String queryString = "from Item item"
    //                         + " where item.description like ?1"
    //                         + " and item.date > ?2";
    //    Query q = em.createQuery(queryString).setParameter(1, searchString)
    //                                          .setParameter(2, minDate, TemporalType.DATE);


    // READ AT PAGE 657 and further!

    tx.commit();
    session.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}

