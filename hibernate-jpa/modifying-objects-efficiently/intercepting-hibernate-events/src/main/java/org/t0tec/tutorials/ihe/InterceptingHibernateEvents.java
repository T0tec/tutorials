package org.t0tec.tutorials.ihe;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.ihe.persistence.AuditLogInterceptor;
import org.t0tec.tutorials.ihe.persistence.AuditLogRecord;
import org.t0tec.tutorials.ihe.persistence.HibernateUtil;

import java.util.List;

public class InterceptingHibernateEvents {

  private static final Logger logger = LoggerFactory.getLogger(InterceptingHibernateEvents.class);

  private long computerId;

  public static void main(String[] args) {
    InterceptingHibernateEvents ihe = new InterceptingHibernateEvents();
    ihe.doFirstUnit();
    ihe.doSecondUnit();
    ihe.doThirdUnit();

    ihe.getAllAuditLogs();
    // Shutting down the application
    HibernateUtil.shutdown();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }

  public void doFirstUnit() {
    // First unit of work
    User currentUser = new User("John", "Doe", "johndoe", "eodnhoj", "johndoe@hibernate.org", 1, true);

    Item newItem = new Item("Foo item", true);

    AuditLogInterceptor interceptor = new AuditLogInterceptor();
    Session session = HibernateUtil.getSessionFactory().openSession(interceptor);
    Transaction tx = session.beginTransaction();

    session.save(currentUser);
    interceptor.setSession(session);
    interceptor.setUserId(currentUser.getId());
    session.save(newItem); // Triggers onSave() of the Interceptor

    tx.commit();
    session.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    StatelessSession session = HibernateUtil.getSessionFactory().openStatelessSession();
    Transaction tx = session.beginTransaction();


    tx.commit();
    session.close();
  }

  private void getAllAuditLogs() {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<AuditLogRecord> auditLogs = listAndCast(newSession.createQuery("from AuditLogRecord alr order by alr.id asc"));
    logger.debug("{} item(s) found", auditLogs.size());
    for (AuditLogRecord alr : auditLogs) {
      logger.debug(alr.toString());
    }
    newTransaction.commit();
    newSession.close();
  }

}
