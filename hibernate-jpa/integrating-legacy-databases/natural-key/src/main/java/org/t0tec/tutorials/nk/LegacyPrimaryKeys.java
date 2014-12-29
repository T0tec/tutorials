package org.t0tec.tutorials.nk;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.nk.persistence.HibernateUtil;

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

    User user = new User();
    user.setUsername("johndoe"); // Assign a primary key value
    user.setFirstname("John");
    user.setLastname("Doe");
    session.saveOrUpdate(user);
    // Will result in an INSERT but does a select first to see if it exists or not
    logger.debug("{}", session.getIdentifier(user));

    user.setAdmin(true);

    session.saveOrUpdate(user);

    session.flush();

    tx.commit();
    session.close();

    // Second unit of work
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
      logger.debug("Version number: {}", u.getVersion());
    }
    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
