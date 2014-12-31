package org.t0tec.tutorials.thi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

import org.hibernate.LockOptions;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.thi.persistence.HibernateUtil;

public class WorkingWithHibernate {

  private static final Logger logger = LoggerFactory.getLogger(WorkingWithHibernate.class);

  public static void main(String[] args) {
    WorkingWithHibernate wwh = new WorkingWithHibernate();
    wwh.doFirstUnit();
    // wwh.doSecondUnit();
    // wwh.doThirdUnit();
    // wwh.doFourthUnit();
    // wwh.doFifthUnit();
    // wwh.doSixthUnit();
    // wwh.doSeventhUnit();
    // wwh.doEightUnit();
    // wwh.doNinthUnit();
    wwh.doTenthUnit();
    // Shutting down the application
    HibernateUtil.shutdown();
  }

  public void doFirstUnit() {
    // First unit of work
    GregorianCalendar gc = new GregorianCalendar();
    gc.setLenient(false);
    gc.set(2015, 0, 31, 14, 33, 48);

    // A new transient object is being created
    Item item = new Item();
    item.setName("Playstation3 incl. all accessories");
    item.setEndDate(gc.getTime());
    item.setInitialPrice(new BigDecimal(199));

    // A new session & transaction is started
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // Makes item persistent
    // It’s now associated with the current Session and its persistence context
    // The save() operation also returns the database identifier of the persistent instance
    Serializable itemId = session.save(item);

    logger.debug("database identifier: {}", itemId);

    // We say a flush occurs (you can also call flush() manually)
    // To synchronize the persistence context
    tx.commit();
    // The Session can finally be closed, and the persistence context ends
    // The reference item is now a reference to an object in detached state.
    session.close();
  }

  // The one difference between get() and load() is how they indicate that the
  // instance could not be found. If no row with the given identifier value exists in the
  // database, get() returns null. The load() method throws an ObjectNotFound-
  // Exception. It’s your choice what error-handling you prefer.
  // More important, the load() method may return a proxy, a placeholder, without
  // hitting the database. A consequence of this is that you may get an ObjectNotFoundException
  // later, as soon as you try to access the returned placeholder and force
  // its initialization (this is also called lazy loading; we discuss load optimization in later
  // chapters.) The load() method always tries to return a proxy, and only returns an
  // initialized object instance if it’s already managed by the current persistence context.
  // In the example shown earlier, no database hit occurs at all! The get()
  // method on the other hand never returns a proxy, it always hits the database.
  // You may ask why this option is useful—after all, you retrieve an object to
  // access it. It’s common to obtain a persistent instance to assign it as a reference to
  // another instance. For example, imagine that you need the item only for a single
  // purpose: to set an association with a Comment: aComment.setForAuction(item).
  // If this is all you plan to do with the item, a proxy will do fine; there is no need to
  // hit the database. In other words, when the Comment is saved, you need the foreign
  // key value of an item inserted into the COMMENT table. The proxy of an Item provides
  // just that: an identifier value wrapped in a placeholder that looks like the real thing.
  public void doSecondUnit() {
    // Second unit of work

    Item item = new Item();
    item.setName("Playstation4 incl. all accessories");

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    session.save(item);

    Item proxyItem = (Item) session.load(Item.class, new Long(2));
    // Item duplicateItem = (Item) session.get(Item.class, new Long(2));

    logger.debug("{}", proxyItem.toString());

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // First, you retrieve the object from the database with the given identifier
    Item item = (Item) session.get(Item.class, new Long(2));
    // You modify the object, and these modifications are propagated to the database during
    // flush when tx.commit() is called.
    item.setDescription("This Playstation 4 is as good as new!");

    logger.debug("{}", item.toString());

    // This mechanism is called automatic dirty checking
    // that means Hibernate tracks and saves the changes you make to an object
    // in persistent state. As soon as you close the Session, the instance is considered detached
    tx.commit();
    session.close();
  }

  public void doFourthUnit() {
    // Fourth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    // First, you retrieve the object from the database with the given identifier
    Item item = (Item) session.get(Item.class, new Long(2));

    // The item object is in removed state after you call delete(); you shouldn’t continue
    // working with it, and, in most cases, you should make sure any reference to it
    // in your application is removed
    session.delete(item);

    // The SQL DELETE is executed only when the Session’s persistence context
    // is synchronized with the database at the end of the unit of work
    tx.commit();
    session.close();
  }

  // The ReplicationMode controls the details of the replication procedure:
  // - ReplicationMode.IGNORE—Ignores the object when there is an existing
  // database row with the same identifier in the target database.
  // - ReplicationMode.OVERWRITE—Overwrites any existing database row with
  // the same identifier in the target database.
  // - ReplicationMode.EXCEPTION—Throws an exception if there is an existing
  // database row with the same identifier in the target database.
  // - ReplicationMode.LATEST_VERSION—Overwrites the row in the target
  // database if its version is earlier than the version of the object, or ignores
  // the object otherwise. Requires enabled Hibernate optimistic concurrency
  // control.
  public void doFifthUnit() {
    // Fifth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Item item = (Item) session.get(Item.class, new Long(1));

    tx.commit();
    session.close();


    Session session2 = HibernateUtil.getDuplicateSessionFactory().openSession();
    Transaction tx2 = session2.beginTransaction();

    session2.replicate(item, ReplicationMode.LATEST_VERSION);

    Item dupItem = (Item) session2.get(Item.class, new Long(1));

    logger.debug(dupItem.toString());

    tx2.commit();
    session2.close();
  }

  // It doesn’t matter if the item object is modified before or after it’s passed to
  // update(). The important thing here is that the call to update() is reattaching the
  // detached instance to the new Session (and persistence context). Hibernate
  // always treats the object as dirty and schedules an SQL UPDATE., which will be executed
  // during flush.
  // You may be surprised and probably hoped that Hibernate could know that you
  // modified the detached item’s description (or that Hibernate should know you did
  // not modify anything). However, the new Session and its fresh persistence context
  // don’t have this information. Neither does the detached object contain some internal
  // list of all the modifications you’ve made. Hibernate has to assume that an
  // UDPATE in the database is needed. One way to avoid this UDPATE statement is to
  // configure the class mapping of Item with the select-before-update="true"
  // attribute. Hibernate then determines whether the object is dirty by executing a
  // SELECT statement and comparing the object’s current state to the current database state.
  // If you’re sure you haven’t modified the detached instance, you may prefer
  // another method of reattachment that doesn’t always schedule an update of the database.
  public void doSixthUnit() {
    // Sixth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Item item = (Item) session.get(Item.class, new Long(1));

    tx.commit();
    session.close();

    item.setDescription("This playstation 3 is in a good state!"); // Loaded in previous Session

    Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
    Transaction tx2 = sessionTwo.beginTransaction();

    sessionTwo.update(item);

    GregorianCalendar gc = new GregorianCalendar();
    gc.setLenient(false);
    gc.set(2015, 0, 24, 9, 12, 34);

    item.setEndDate(gc.getTime());

    tx2.commit();
    sessionTwo.close();

    logger.debug(item.toString());
  }

  // In this case, it does matter whether changes are made before or after the object has
  // been reattached. Changes made before the call to lock() aren’t propagated to
  // the database, you use it only if you’re sure the detached instance hasn’t been
  // modified. This method only guarantees that the object’s state changes from
  // detached to persistent and that Hibernate will manage the persistent object again.
  // Of course, any modifications you make to the object once it’s in managed persistent
  // state require updating of the database.
  // We discuss Hibernate lock modes in the next chapter. By specifying Lock-
  // Mode.NONE here, you tell Hibernate not to perform a version check or obtain any
  // database-level locks when reassociating the object with the Session. If you specified
  // LockMode.READ, or LockMode.UPGRADE, Hibernate would execute a SELECT statement
  // in order to perform a version check (and to lock the row(s) in the database for updating).
  public void doSeventhUnit() {
    // Seventh unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Item item = (Item) session.get(Item.class, new Long(1));

    tx.commit();
    session.close();

    Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
    Transaction tx2 = sessionTwo.beginTransaction();
    // Changes made before the call to lock() aren’t propagated to the database
    item.setInitialPrice(new BigDecimal(179));

    // sessionTwo.lock(item, LockMode.NONE);
    sessionTwo.buildLockRequest(LockOptions.NONE).lock(item);
    item.setDescription("This playstation 3 is in a fine state");
    GregorianCalendar gc = new GregorianCalendar();
    gc.setLenient(false);
    gc.set(2015, 0, 31, 9, 12, 34);

    item.setEndDate(gc.getTime());

    item = (Item) sessionTwo.get(Item.class, new Long(1));

    tx2.commit();
    sessionTwo.close();

    logger.debug(item.toString()); // still changes are made to initialPrice propery
  }

  // This means you don’t have to reattach (with update() or lock()) a detached
  // instance to delete it from the database. In this case, the call to delete() does two
  // things: It reattaches the object to the Session and then schedules the object for
  // deletion, executed on tx.commit(). The state of the object after the delete()
  // call is removed.
  // Reattachment of detached objects is only one possible way to transport data
  // between several Sessions. You can use another option to synchronize modifications
  // to a detached instance with the database, through merging of its state.
  public void doEightUnit() {
    // Eight unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Item item = (Item) session.get(Item.class, new Long(1));

    tx.commit();
    session.close();

    Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
    Transaction tx2 = sessionTwo.beginTransaction();

    sessionTwo.delete(item);

    item = (Item) sessionTwo.get(Item.class, new Long(1));

    tx2.commit();
    sessionTwo.close();

    if (item == null) {
      logger.debug("item doesn't exist anymore in the database!");
    }

  }

  // Given is a detached item object with the database identity 1234. After modifying
  // it, you try to reattach it to a new Session. However, before reattachment, another
  // instance that represents the same database row has already been loaded into the
  // persistence context of that Session. Obviously, the reattachment through
  // update() clashes with this already persistent instance, and a NonUniqueObjectException
  // is thrown. The error message of the exception is A persistent instance with
  // the same database identifier is already associated with the Session! Hibernate can’t decide
  // which object represents the current state.
  // You can resolve this situation by reattaching the item first; then, because the
  // object is in persistent state, the retrieval of item2 is unnecessary. This is straightforward
  // in a simple piece of code such as the example, but it may be impossible to
  // refactor in a more sophisticated application. After all, a client sent the detached
  // object to the persistence layer to have it managed, and the client may not (and
  // shouldn’t) be aware of the managed instances already in the persistence context.
  // You can let Hibernate merge item and item2 automatically: see TenthUnit()
  public void doNinthUnit() {
    // Ninth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Item item = (Item) session.get(Item.class, new Long(1));

    tx.commit();
    session.close();

    logger.debug(item.getId().toString());
    item.setDescription("This Playstation 3 is from 2008!");

    Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
    Transaction tx2 = sessionTwo.beginTransaction();

    Item item2 = (Item) sessionTwo.get(Item.class, new Long(1));
    sessionTwo.update(item); // Throws exception!

    tx2.commit();
    sessionTwo.close();
  }

  // The merge(item) call results in several actions. First, Hibernate checks
  // whether a persistent instance in the persistence context has the same database
  // identifier as the detached instance you’re merging. In this case, this is true: item
  // and item2, which were loaded with get() , have the same primary key value.
  // If there is an equal persistent instance in the persistence context, Hibernate
  // copies the state of the detached instance onto the persistent instance. In other
  // words, the new description that has been set on the detached item is also set on
  // the persistent item2.
  // If there is no equal persistent instance in the persistence context, Hibernate
  // loads it from the database (effectively executing the same retrieval by identifier as
  // you did with get()) and then merges the detached state with the retrieved
  // object’s state.
  public void doTenthUnit() {
    // Tenth unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Item item = (Item) session.get(Item.class, new Long(1));

    tx.commit();
    session.close();

    logger.debug(item.getId().toString());
    item.setDescription("This Playstation 3 is from 2008!");

    Session sessionTwo = HibernateUtil.getSessionFactory().openSession();
    Transaction tx2 = sessionTwo.beginTransaction();

    Item item2 = (Item) sessionTwo.get(Item.class, new Long(1));
    Item item3 = (Item) sessionTwo.merge(item);
    logger.debug("{}", item == item2); // False
    logger.debug("{}", item == item3);// False
    logger.debug("{}", item2 == item3); // True

    tx2.commit();
    sessionTwo.close();
  }
}
