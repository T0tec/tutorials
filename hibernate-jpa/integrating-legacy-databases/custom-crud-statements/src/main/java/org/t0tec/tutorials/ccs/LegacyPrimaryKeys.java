package org.t0tec.tutorials.ccs;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.ccs.persistence.HibernateUtil;

public class LegacyPrimaryKeys {

  private static final Logger logger = LoggerFactory.getLogger(LegacyPrimaryKeys.class);

  private long userId;

  public static void main(String[] args) {
    LegacyPrimaryKeys lpk = new LegacyPrimaryKeys();
    lpk.doFirstUnit();
    lpk.doSecondUnit();
    // lpk.doThirdUnit();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  public void doFirstUnit() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    BankAccount bankAccount =
        new BankAccount("John Doe", "Savings account", "Nil Percent Bank Inc.", "2015");
    session.save(bankAccount);

    CreditCard creditCard =
        new CreditCard("John Doe", "9876-1234-5678-0009", CreditCardType.MASTERCARD, "10", "2015");
    session.save(creditCard);

    User user = new User("John", "Doe", "johndoe", "oednhoj", "johndoe@hibernate.org", 1, true);
    user.addBillingDetails(bankAccount);
    user.addBillingDetails(creditCard);
    user.setDefaultBillingDetails(creditCard);
    user.setBillingAddress(new Address("Doe street", "90000", "Doe city"));

    userId = (Long) session.save(user);

    logger.debug("{}", userId);

    Item fooItem = new Item("Foo item", user);
    Item barItem = new Item("Bar item", user);

    session.save(fooItem);
    session.save(barItem);

    tx.commit();
    session.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query q = session.getNamedQuery("loadUser").setLong("user_id", userId);
    User loadUser = (User) q.uniqueResult();

    logger.debug(loadUser.toString());
    logger.debug(loadUser.getDefaultBillingDetails().toString());
    logger.debug(loadUser.getBillingAddress().toString());
    logger.debug("amount of items for sale: " + loadUser.getItemsForSale().size());

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third Unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();


    List<Item> items =
        listAndCast(session.getNamedQuery("loadItemsForUser").setParameter("seller_id", userId));

    logger.debug("items.size(): {}", items.size());

    tx.commit();
    session.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
