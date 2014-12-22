package org.t0tec.tutorials.tpcp;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.tpcp.persistence.HibernateUtil;

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
    Long bankAccountId = (Long) session.save(bankAccount);
    tx.commit();
    session.close();

    // second unit of work
    getAllBankAccounts();

    // Third unit of work
    Session secondSession = HibernateUtil.getSessionFactory().openSession();
    Transaction secondTransaction = secondSession.beginTransaction();
    bankAccount = (BankAccount) secondSession.get(BankAccount.class, bankAccountId);
    bankAccount.setOwnername("John Doe & Sharon Doe");
    secondTransaction.commit();
    secondSession.close();

    // Fourth unit of work
    getAllBankAccounts();

    // Fifth unit of work
    Session thirdSession = HibernateUtil.getSessionFactory().openSession();
    Transaction thirdTransaction = thirdSession.beginTransaction();
    CreditCard creditCard =
        new CreditCard("John Doe", "9876-1234-5678-0009", CreditCardType.MASTERCARD, "10", "2015");
    thirdSession.save(creditCard);
    thirdTransaction.commit();
    thirdSession.close();

    // fifth unit of work
    getAllCreditCards();

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

  // This method won't work
  // http://stackoverflow.com/questions/1427439/selecting-mappedsuperclass-from-the-database-hibernate
  @SuppressWarnings("unused")
  private void getAllBillingDetails() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<BillingDetails> billingDetails =
        listAndCast(newSession.createQuery(
            "from BillingDetails bd where bd.owner=:owner order by bd.number asc").setParameter(
            "owner", "John Doe"));
    logger.debug("{} credit card(s) found", billingDetails.size());
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
