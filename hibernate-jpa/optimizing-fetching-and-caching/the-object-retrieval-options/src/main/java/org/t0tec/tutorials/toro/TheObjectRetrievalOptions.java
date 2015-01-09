package org.t0tec.tutorials.toro;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.toro.persistence.HibernateUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

public class TheObjectRetrievalOptions {

  private static final Logger logger = LoggerFactory.getLogger(TheObjectRetrievalOptions.class);

  private long itemId;

  public static void main(String[] args) {
    TheObjectRetrievalOptions toro = new TheObjectRetrievalOptions();
    toro.doFirstUnit();
    toro.doSecondUnit();
    toro.doThirdUnit();
    toro.doFourthUnit();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  public void doFirstUnit() {
    // First unit of work
    GregorianCalendar gc = new GregorianCalendar();
    gc.setLenient(false);
    gc.set(2015, 0, 31, 14, 33, 48);

    Item item = new Item();
    item.setName("Playstation3 incl. all accessories");
    item.setEndDate(gc.getTime());
    item.setInitialPrice(new BigDecimal(199));

    Address johndoesAddress = new Address("Doe street", "90000", "Doe city");

    User johndoe = new User("John", "Doe", "johndoe", "eodnhoj", "johndoe@hibernate.org", 1, true);
    johndoe.setHomeAddress(johndoesAddress);

    User johnhart =
        new User("John", "Hart", "johnhart", "trahnhoj", "johnhart@hibernate.org", 3, false);
    User suedoe = new User("Sue", "Doe", "suedoe", "eodeus", "suedoe@hibernate.org", 2, false);

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    session.save(johndoe);
    session.save(johnhart);
    session.save(suedoe);

    itemId = (Long) session.save(item);

    tx.commit();
    session.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query q = session.createQuery("from User as u where u.firstname = :fname");
    q.setString("fname", "John");

    List<User> result = q.list();

    for (User user : result) {
      logger.debug(user.toString());
    }

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Criteria criteria = session.createCriteria(User.class);
    criteria.add(Restrictions.like("firstname", "John"));
    List<User> result = criteria.list();

    for (User user : result) {
      logger.debug(user.toString());
    }

    tx.commit();
    session.close();
  }

  // TODO: Restriction not working properyly (see chapter 15 for more info)
  public void doFourthUnit() {
    // Fourth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Criteria criteria = session.createCriteria(User.class);
    User exampleUser = new User();
    exampleUser.setFirstname("John");
    criteria.add(Example.create(exampleUser));
    criteria.add(Restrictions.isNotNull("homeAddress.city"));
    List<User> result = criteria.list();

    logger.debug("size: {}", result.size());

    for (User user : result) {
      logger.debug(user.toString());
    }

    tx.commit();
    session.close();
  }
}
