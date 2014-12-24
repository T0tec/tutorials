package org.t0tec.tutorials.spka;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.spka.persistence.HibernateUtil;

public class SharedPKAssociation {

  private static final Logger logger = LoggerFactory.getLogger(SharedPKAssociation.class);

  public static void main(String[] args) {
    SharedPKAssociation spka = new SharedPKAssociation();
    spka.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    User newUser = new User("John", "Doe", "johndoe", "eodnhoj", "johndoe@hibernate.org", 1, true);
    Address shippingAddress = new Address("Doe street", "90000", "Doe city");
    newUser.setShippingAddress(shippingAddress);
    shippingAddress.setUser(newUser); // Bidirectional

    session.save(newUser);

    tx.commit();
    session.close();

    // second unit of work
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
