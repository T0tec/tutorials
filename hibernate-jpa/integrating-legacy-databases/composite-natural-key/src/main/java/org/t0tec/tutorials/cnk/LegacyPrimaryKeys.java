package org.t0tec.tutorials.cnk;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.cnk.persistence.HibernateUtil;

public class LegacyPrimaryKeys {

  private static final Logger logger = LoggerFactory.getLogger(LegacyPrimaryKeys.class);

  public static void main(String[] args) {
    LegacyPrimaryKeys lpk = new LegacyPrimaryKeys();
    lpk.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    UserId id = new UserId("johndoe", 42);

    User user = new User();
    // Assign a primary key value
    user.setUserId(id);
    // Set property values
    user.setFirstname("John");
    user.setLastname("Doe");

    session.saveOrUpdate(user);
    session.flush();

    logger.debug("{}", session.getIdentifier(user));

    tx.commit();
    session.close();

    // Second unit of work
    loadInstance();
    getAllUsers();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  private void getAllUsers() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<User> users = listAndCast(newSession.createQuery("from User u order by u.id asc"));
    logger.debug("{} user(s) found", users.size());
    for (User u : users) {
      logger.debug(u.toString());
    }
    newTransaction.commit();
    newSession.close();
  }

  private void loadInstance() {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    UserId id = new UserId("johndoe", 42);
    User user = (User) session.load(User.class, id);

    logger.debug(user.toString());

    tx.commit();
    session.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
