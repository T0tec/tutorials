package org.t0tec.tutorials.otmwjt;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.otmwjt.persistence.HibernateUtil;

public class OneToManyWithJoinTable {

  private static final Logger logger = LoggerFactory.getLogger(OneToManyWithJoinTable.class);

  public static void main(String[] args) {
    OneToManyWithJoinTable otmwjt = new OneToManyWithJoinTable();
    otmwjt.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Shipment aShipment = new Shipment();
    Item anItem = new Item("Foo");
    aShipment.setAuction(anItem);

    session.save(anItem);
    session.save(aShipment);

    tx.commit();
    session.close();

    // second unit of work
    getAllShipments();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  private void getAllShipments() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<Shipment> shipments =
        listAndCast(newSession.createQuery("from Shipment s order by s.id asc"));
    logger.debug("{} shipment(s) found", shipments.size());
    for (Shipment s : shipments) {
      logger.debug(s.toString());
    }
    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
