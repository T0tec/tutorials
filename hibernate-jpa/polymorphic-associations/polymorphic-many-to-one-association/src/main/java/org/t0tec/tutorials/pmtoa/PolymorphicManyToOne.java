package org.t0tec.tutorials.pmtoa;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.pmtoa.persistence.HibernateUtil;

public class PolymorphicManyToOne {

  private static final Logger logger = LoggerFactory.getLogger(PolymorphicManyToOne.class);

  private long userId;

  public static void main(String[] args) {
    PolymorphicManyToOne pmto = new PolymorphicManyToOne();
    pmto.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    BankAccount bankAccount =
        new BankAccount("John Doe", "Savings account", "Nil Percent Bank Inc.", "2015");
    session.save(bankAccount);

    tx.commit();
    session.close();

    // Second unit of work
    Session secondSession = HibernateUtil.getSessionFactory().openSession();
    Transaction secondTransaction = secondSession.beginTransaction();

    CreditCard creditCard =
        new CreditCard("John Doe", "9876-1234-5678-0009", CreditCardType.MASTERCARD, "10", "2015");
    secondSession.save(creditCard);

    secondTransaction.commit();
    secondSession.close();


    // Third unit of work
    Session thirdSession = HibernateUtil.getSessionFactory().openSession();
    Transaction thirdTransaction = thirdSession.beginTransaction();

    User user = new User("John", "Doe", "johndoe", "oednhoj", "jonhdoe@hibernate.org", 1, true);
    userId = (Long) thirdSession.save(user);

    user = (User) thirdSession.get(User.class, userId);
    user.addBillingDetails(creditCard); // Add it to the one-to-many association
    user.setDefaultBillingDetails(creditCard);

    logger.debug("{}", user.getDefaultBillingDetails().toString());

    thirdTransaction.commit();
    thirdSession.close();

    // Fourth unit of work
    Session fourthSession = HibernateUtil.getSessionFactory().openSession();
    Transaction fourthTransaction = fourthSession.beginTransaction();

    user = (User) fourthSession.get(User.class, userId);
    // Invoke the pay() method on the actual subclass instance
    user.getDefaultBillingDetails().pay(new BigDecimal(499));

    fourthTransaction.commit();
    fourthSession.close();

    // Fifth unit of work
    Session fifthSession = HibernateUtil.getSessionFactory().openSession();
    Transaction fifthTransaction = fifthSession.beginTransaction();

    user = (User) fifthSession.get(User.class, userId);
    BillingDetails bd = user.getDefaultBillingDetails();
    // logger.debug("{}", bd instanceof CreditCard); // Prints "false"
    // CreditCard cc = (CreditCard) bd; // ClassCastException!

    fifthTransaction.commit();
    fifthSession.close();

    // Sixth unit of work
    Session sixthSession = HibernateUtil.getSessionFactory().openSession();
    Transaction sixthTransaction = sixthSession.beginTransaction();

    user = (User) sixthSession.get(User.class, userId);
    bd = user.getDefaultBillingDetails();
    // Narrow the proxy to the subclass, doesn't hit the database
    CreditCard cc = (CreditCard) sixthSession.load(CreditCard.class, bd.getId());

    logger.debug(cc.getOwner());

    sixthTransaction.commit();
    sixthSession.close();

    // Seventh unit of work
    Session seventhSession = HibernateUtil.getSessionFactory().openSession();
    Transaction seventhTransaction = seventhSession.beginTransaction();

    user =
        (User) seventhSession.createCriteria(User.class).add(Restrictions.eq("id", userId))
            .setFetchMode("defaultBillingDetails", FetchMode.JOIN).uniqueResult();
    // The users defaultBillingDetails have been fetched eagerly
    cc = (CreditCard) user.getDefaultBillingDetails();

    logger.debug(cc.getOwner());

    seventhTransaction.commit();
    seventhSession.close();

    // Last unit of work
    getAllBankAccounts();
    getAllCreditCards();
    getAllBillingDetails();


    // Shutting down the application
    HibernateUtil.shutdown();
  }

  private void getAllBankAccounts() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<BankAccount> accounts =
        listAndCast(newSession.createQuery("from BankAccount ba order by ba.account asc"));
    logger.debug("{} bank account(s) found", accounts.size());
    for (BankAccount bankAccount : accounts) {
      logger.debug(bankAccount.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  private void getAllCreditCards() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<CreditCard> creditcards =
        listAndCast(newSession.createQuery("from CreditCard cc order by cc.number asc"));
    logger.debug("{} credit card(s) found", creditcards.size());
    for (CreditCard creditCard : creditcards) {
      logger.debug(creditCard.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  private void getAllBillingDetails() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<BillingDetails> billingDetails =
        listAndCast(newSession.createQuery(
            "from BillingDetails bd where bd.owner=:owner order by bd.number asc").setParameter(
            "owner", "John Doe"));
    logger.debug("{} billing detail(s) found", billingDetails.size());
    for (BillingDetails bd : billingDetails) {
      logger.debug(bd.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
