package org.t0tec.tutorials.tjpai;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkingWithJPA {
  private static final Logger logger = LoggerFactory.getLogger(WorkingWithJPA.class);

  private static EntityManagerFactory emf;

  public static void main(String[] args) {
    // Start EntityManagerFactory
    emf = Persistence.createEntityManagerFactory("jpa");
    doFirstUnit();
    doSecondUnit();
    doThirdUnit();
    doFourthUnit();
    doFifthUnit();
    doSixthUnit();
    doSeventhUnit();
    doTenthUnit();
    // Shutting down the application
    emf.close();
  }

  // Should I use persist() on the Session? The Hibernate Session interface also
  // features a persist() method. It has the same semantics as the persist()
  // operation of JPA. However, there’s an important difference between the
  // two operations with regard to flushing. During synchronization, a Hibernate
  // Session doesn’t cascade the persist() operation to associated entities
  // and collections, even if you mapped an association with this option.
  // It’s only cascaded to entities that are reachable when you call persist()!
  // Only save() (and update()) are cascaded at flush-time if you use the
  // Session API. In a JPA application, however, it’s the other way round:
  // Only persist() is cascaded at flush-time.
  public static void doFirstUnit() {
    // First unit of work
    GregorianCalendar gc = new GregorianCalendar();
    gc.setLenient(false);
    gc.set(2015, 0, 31, 14, 33, 48);

    // A new transient object is being created
    Item item = new Item();
    item.setName("Playstation3 incl. all accessories");
    item.setEndDate(gc.getTime());
    item.setInitialPrice(new BigDecimal(199));

    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    em.persist(item);

    tx.commit();
    em.close();
  }


  public static void doSecondUnit() {
    // Second unit of work
    Item item = new Item();
    item.setName("Playstation4 incl. all accessories");

    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    em.persist(item);

    item = em.find(Item.class, new Long(2));

    tx.commit();
    em.close();

    logger.debug(item.toString());

    // This operation returns either the fully initialized item (for example, if the
    // instance was already available in the current persistence context) or a proxy (a hollow
    // placeholder).
    // As soon as you try to access any property of the item that isn’t the database
    // identifier property, an additional SELECT is executed to fully initialize the placeholder.
    // This also means you should expect an EntityNotFoundException at this
    // point (or even earlier, when getReference() is executed).
    EntityManager em2 = emf.createEntityManager();
    EntityTransaction tx2 = em2.getTransaction();
    tx2.begin();

    Item proxyItem = em2.getReference(Item.class, new Long(1));

    logger.debug(proxyItem.toString()); // does a select here

    tx2.commit();
    em2.close();

    // Error here if logger above is disabled:
    // LazyInitializationException: could not initialize proxy - no Session
    // logger.debug(proxyItem.toString());
  }


  public static void doThirdUnit() {
    // Third unit of work
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Item item = em.find(Item.class, new Long(2));
    item.setDescription("This playstation 4 is as good as new!");

    tx.commit();
    em.close();

    logger.debug(item.toString());
  }

  public static void doFourthUnit() {
    // Fourth unit of work
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Item item = em.find(Item.class, new Long(2));
    em.remove(item);

    tx.commit();
    em.close();
  }

  // JPA implementations are
  // allowed to synchronize the persistence context at other times, if they wish.
  // Hibernate, as a JPA implementation, synchronizes at the following times:
  // - When an EntityTransaction is committed
  // - Before a query is executed
  // - When the application calls em.flush() explicitly
  // Switching the FlushModeType to COMMIT for an EntityManager disables automatic
  // synchronization before queries; it occurs only when the transaction is committed
  // or when you flush manually. The default FlushModeType is AUTO.
  // Just as with native Hibernate
  public static void doFifthUnit() {
    // Fifth unit of work
    EntityManager em = emf.createEntityManager();
    em.setFlushMode(FlushModeType.COMMIT);
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Item item = em.find(Item.class, new Long(1));
    item.setDescription("This playstation 3 is in a fine state!");

    @SuppressWarnings("unchecked")
    List<Item> result = em.createQuery("from Item i order by i.id asc").getResultList();

    logger.debug("{}", result.toString());

    tx.commit();
    em.close();
  }

  // In the first transaction, you retrieve an Item object. The transaction then completes,
  // but the item is still in persistent state. Hence, in the second transaction,
  // you not only load a User object, but also update the modified persistent item when
  // the second transaction is committed (in addition to an update for the dirty user instance).
  // Just like in native Hibernate code with a Session, the persistence context
  // begins with createEntityManager() and ends with close().
  public static void doSixthUnit() {
    // Sixth unit of work
    User newUser = new User();
    newUser.setFirstname("John");
    newUser.setLastname("Doe");
    newUser.setUsername("johndoe");

    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    em.persist(newUser);

    Item item = em.find(Item.class, new Long(1));

    tx.commit();

    item.setDescription("This playstation 3 is in a good state!");

    tx.begin();

    User user = em.find(User.class, new Long(1));
    user.setPassword("eodnhoj");

    tx.commit();
    em.close();
  }

  // After the item is retrieved, you clear the persistence context of the EntityManager.
  // All entity instances that have been managed by that persistence context are
  // now detached. The modification of the detached instance isn’t synchronized with
  // the database during commit.
  public static void doSeventhUnit() {
    // Seventh unit of work
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Item item = em.find(Item.class, new Long(1));

    em.clear();

    item.setDescription("This playstation 3 is in a bad state"); // Detached entity instance!

    tx.commit();
    em.close();
  }

  // The item is retrieved in a first persistence context and merged, after modification
  // in detached state, into a new persistence context. The merge() operation does
  // several things:
  // First, the Java Persistence engine checks whether a persistent instance in the
  // persistence context has the same database identifier as the detached instance
  // you’re merging. Because, in our code examples, there is no equal persistent
  // instance in the second persistence context, one is retrieved from the database
  // through lookup by identifier. Then, the detached entity instance is copied onto the
  // persistent instance. In other words, the new description that has been set on the
  // detached item is also set on the persistent mergedItem, which is returned from
  // the merge() operation.
  // If there is no equal persistent instance in the persistence context, and a lookup
  // by identifier in the database is negative, the merged instance is copied onto a
  // fresh persistent instance, which is then inserted into the database when the second
  // persistence context is synchronized with the database.
  // Merging of state is an alternative to reattachment
  public static void doTenthUnit() {
    // Tenth unit of work
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();

    Item item = em.find(Item.class, new Long(1));

    tx.commit();
    em.close();

    item.setInitialPrice(new BigDecimal(179)); // Detached entity instance!

    EntityManager em2 = emf.createEntityManager();
    EntityTransaction tx2 = em2.getTransaction();
    tx2.begin();

    Item mergedItem = (Item) em2.merge(item);

    tx2.commit();
    em2.close();

    logger.debug(mergedItem.toString());
  }
}
