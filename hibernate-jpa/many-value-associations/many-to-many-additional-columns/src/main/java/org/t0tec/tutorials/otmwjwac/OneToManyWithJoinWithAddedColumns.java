package org.t0tec.tutorials.otmwjwac;

import java.util.HashSet;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.otmwjwac.persistence.HibernateUtil;

public class OneToManyWithJoinWithAddedColumns {

  private static final Logger logger = LoggerFactory
      .getLogger(OneToManyWithJoinWithAddedColumns.class);

  public static void main(String[] args) {
    OneToManyWithJoinWithAddedColumns otmwjwac = new OneToManyWithJoinWithAddedColumns();
    otmwjwac.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    User aUser = new User("John", "Doe", "johndoe", "oednhoj", "johndoe@hibernate.org", 1, true);
    Category aCategory = new Category("Category 1", new HashSet<CategorizedItem>());
    Item anItem = new Item("Item 1", new HashSet<CategorizedItem>());

    session.save(aUser);
    session.save(aCategory);
    session.save(anItem);

    CategorizedItem newLink = new CategorizedItem(aUser.getUsername(), aCategory, anItem);
    session.save(newLink);

    tx.commit();
    session.close();

    // second unit of work
    getAllCategorizedItems();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  private void getAllCategorizedItems() throws HibernateException {
    Session newSession = HibernateUtil.getSessionFactory().openSession();
    Transaction newTransaction = newSession.beginTransaction();

    List<CategorizedItem> catItems =
        listAndCast(newSession.createQuery("from CategorizedItem ci order by ci.id asc"));
    logger.debug("{} categorized item(s) found", catItems.size());
    for (CategorizedItem catItem : catItems) {
      logger.debug(catItem.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
