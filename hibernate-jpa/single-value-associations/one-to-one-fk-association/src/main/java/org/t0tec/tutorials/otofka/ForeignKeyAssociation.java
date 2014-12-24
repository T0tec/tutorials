package org.t0tec.tutorials.otofka;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.otofka.persistence.HibernateUtil;

public class ForeignKeyAssociation {

  private static final Logger logger = LoggerFactory.getLogger(ForeignKeyAssociation.class);

  public static void main(String[] args) {
    ForeignKeyAssociation fka = new ForeignKeyAssociation();
    fka.doWork();
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
