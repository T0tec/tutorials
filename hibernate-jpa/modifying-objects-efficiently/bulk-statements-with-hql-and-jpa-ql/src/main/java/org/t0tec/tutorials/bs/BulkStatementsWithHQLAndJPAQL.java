package org.t0tec.tutorials.bs;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.bs.persistence.HibernateUtil;

import java.util.Date;
import java.util.List;

public class BulkStatementsWithHQLAndJPAQL {

  private static final Logger logger = LoggerFactory.getLogger(BulkStatementsWithHQLAndJPAQL.class);

  private long computerId;

  public static void main(String[] args) {
    BulkStatementsWithHQLAndJPAQL pbr = new BulkStatementsWithHQLAndJPAQL();
    pbr.doFirstUnit();
    pbr.doSecondUnit();
    pbr.doThirdUnit();
    pbr.doFourthUnit();
    pbr.getAllItems();
    pbr.doFifthUnit();
    pbr.doSixthUnit();
    pbr.getAllStolenCreditCards();
    // Shutting down the application
    HibernateUtil.shutdown();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }

  public void doFirstUnit() {
    // First unit of work
    Item fooItem = new Item("Foo item");
    fooItem.setActive(false);
    Item barItem = new Item("Bar item");
    barItem.setActive(false);

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    session.save(fooItem);
    session.save(barItem);

    tx.commit();
    session.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query q =
        session.createQuery("update Item i set i.isActive = :isActive");
    q.setBoolean("isActive", true);
    int updatedItems = q.executeUpdate();

    logger.debug("Update count: {}", updatedItems);

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query q =
        session.createQuery(
            "update versioned Item i set i.isActive = :isActive"
        );
    q.setBoolean("isActive", false);
    int updatedItems = q.executeUpdate();

    logger.debug("Update count: {}", updatedItems);

    tx.commit();
    session.close();
  }

  public void doFourthUnit() {
    // Fourth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query
        q =
        session.createQuery("delete Item i where i.isActive = :isActive")
            .setBoolean("isActive", false);
    int updatedItems = q.executeUpdate();

    logger.debug("Update count: {}", updatedItems);

    tx.commit();
    session.close();
  }

  public void doFifthUnit() {
    // Fifth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    User user = new User("John", "Doe", "johndoe", "oednhoj", "johndoe@hibernate.org", 1, true);
    session.save(user);

    Address homeAddress = new Address("Doe street", "90000", "Doe city");

    user.setHomeAddress(homeAddress);

    CreditCard
        cc =
        new CreditCard(user.getUsername(), "1234-4567-8912-3456", CreditCardType.MASTERCARD, "10",
                       "2015");
    cc.setStolenOn(new Date());
    session.save(cc);

//    StolenCreditCard scc = new StolenCreditCard(cc.getExpMonth(), cc.getExpYear(), user.getEmail(),
//                                                user.getFirstname(), user.getHomeAddress().getStreet(),
//                                                user.getLastname(), user.getUsername(),
//                                                cc.getNumber(), cc.getType());
//    session.save(scc);

    tx.commit();
    session.close();
  }

  // TODO: Insertion for unknown reason not working...
  //  insert
  //      into
  //  STOLEN_CREDITCARD
  //      ( TYPE, NUMBER, EXP_MONTH, EXP_YEAR, OWNER_FIRSTNAME, OWNER_LASTNAME, OWNER_LOGIN, OWNER_EMAIL, OWNER_HOME_ADDRESS ) select
  //  creditcard0_.TYPE as col_0_0_,
  //  creditcard0_.NUMBER as col_1_0_,
  //  creditcard0_.EXP_MONTH as col_2_0_,
  //  creditcard0_.EXP_YEAR as col_3_0_,
  //  user1_.FIRSTNAME as col_4_0_,
  //  user1_.LASTNAME as col_5_0_,
  //  user1_.USERNAME as col_6_0_,
  //  user1_.EMAIL as col_7_0_,
  //  user1_1_.HOME_STREET as col_8_0_
  //      from
  //  CREDIT_CARD creditcard0_
  //  inner join
  //  USER user1_
  //  on creditcard0_.USER_ID=user1_.USER_ID
  //  left outer join
  //  HOME_ADDRESS user1_1_
  //  on user1_.USER_ID=user1_1_.USER_ID
  //      where
  //  creditcard0_.STOLEN_ON is not null
  public void doSixthUnit() {
    // Sixth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Query q = session.createQuery(
        "insert into StolenCreditCard (type, number, expMonth, expYear, ownerFirstname, "
        + "ownerLastname, ownerLogin, ownerEmailAddress, ownerHomeAddress) "
        + "select c.type, c.number, "
        + "c.expMonth, c.expYear, u.firstname, u.lastname, u.username, u.email, u.homeAddress.street from "
        + "CreditCard c join c.user u" // where c.stolenOn is not null
    );

    int createdObjects = q.executeUpdate();

    logger.debug("created objects: {}", createdObjects);

    tx.commit();
    session.close();
  }

  private void getAllItems() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<Item> items = listAndCast(newSession.createQuery("from Item i order by i.id asc"));
    logger.debug("{} item(s) found", items.size());
    for (Item i : items) {
      logger.debug(i.toString());
    }
    newTransaction.commit();
    newSession.close();
  }

  private void getAllStolenCreditCards() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<StolenCreditCard>
        sccs =
        listAndCast(newSession.createQuery("from StolenCreditCard scc order by scc.id asc"));
    logger.debug("{} stolen creditcard(s) found", sccs.size());
    for (StolenCreditCard scc : sccs) {
      logger.debug(scc.toString());
    }
    newTransaction.commit();
    newSession.close();
  }

}
