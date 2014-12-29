package org.t0tec.tutorials.cfkrnpk;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.cfkrnpk.persistence.HibernateUtil;

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
    // Set property values
    user.setFirstname("John");
    user.setLastname("Doe");
    user.setBirthday(GregorianCalendar.getInstance().getTime());

    session.saveOrUpdate(user);
    session.flush();
    // logger.debug("{}", session.getIdentifier(user));
    // Second unit of work
    Item fooItem = new Item("Foo");
    Item barItem = new Item("Bar");
    session.save(fooItem);
    session.save(barItem);

    fooItem.setSeller(user);
    barItem.setSeller(user);

    HashSet<Item> itemsForAuction = new HashSet<Item>();
    itemsForAuction.add(fooItem);
    itemsForAuction.add(barItem);

    user.setItemsForAuction(itemsForAuction);

    logger.debug(user.toString());

    tx.commit();
    session.close();

    // Last unit of work
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
