package org.t0tec.tutorials.tpcu;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.tpcu.persistence.HibernateUtil;

public class TablePerClass {

  private static final Logger logger = LoggerFactory.getLogger(TablePerClass.class);

  public static void main(String[] args) {
    TablePerClass tpc = new TablePerClass();
    tpc.doWork();
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
    getAllBankAccounts();

    // Third unit of work
    getAllBankAccounts();

    // Fourth unit of work
    Session secondSession = HibernateUtil.getSessionFactory().openSession();
    Transaction secondTransaction = secondSession.beginTransaction();
    CreditCard creditCard =
        new CreditCard("John Doe", "9876-1234-5678-0009", CreditCardType.MASTERCARD, "10", "2015");
    secondSession.save(creditCard);
    secondTransaction.commit();
    secondSession.close();

    // Fifth unit of work
    getAllCreditCards();

    // Sixth unit of work
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
