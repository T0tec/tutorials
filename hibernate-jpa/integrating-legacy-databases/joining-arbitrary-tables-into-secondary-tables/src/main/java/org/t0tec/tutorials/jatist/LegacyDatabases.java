package org.t0tec.tutorials.jatist;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.jatist.persistence.HibernateUtil;

public class LegacyDatabases {

  private static final Logger logger = LoggerFactory.getLogger(LegacyDatabases.class);

  public static void main(String[] args) {
    LegacyDatabases ld = new LegacyDatabases();
    ld.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    User user = new User("John", "Doe", "johndoe", "oednhoj", "johndoe@hibernate.org", 1, true);
    session.save(user);

    Address homeAddress = new Address("Doe street", "90000", "Doe city");

    user.setBillingAddress(homeAddress);

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
    }
    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
