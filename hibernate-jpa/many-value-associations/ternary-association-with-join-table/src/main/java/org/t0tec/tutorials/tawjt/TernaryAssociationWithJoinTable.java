package org.t0tec.tutorials.tawjt;

import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.tawjt.persistence.HibernateUtil;

public class TernaryAssociationWithJoinTable {

  private static final Logger logger = LoggerFactory
      .getLogger(TernaryAssociationWithJoinTable.class);

  public static void main(String[] args) {
    TernaryAssociationWithJoinTable tawjt = new TernaryAssociationWithJoinTable();
    tawjt.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Category aCategory = new Category("Category Foo", new HashMap<Item, User>());
    Item anItem = new Item("Item Bar", new HashMap<Long, Bid>());
    User aUser = new User("John", "Doe", "johndoe", "oednhoj", "johndoe@hibernate.org", 1, true);

    session.save(aCategory);
    session.save(anItem);
    session.save(aUser);

    aCategory.getItemsAndUser().put(anItem, aUser);

    tx.commit();
    session.close();

    // second unit of work
    getAllCategories();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  private void getAllCategories() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<Category> categories =
        listAndCast(newSession.createQuery("from Category c order by c.id asc"));
    logger.debug("{} Category(ies) found", categories.size());
    for (Category c : categories) {
      logger.debug(c.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
