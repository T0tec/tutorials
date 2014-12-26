package org.t0tec.tutorials.otmwjtcoc;

import java.util.HashSet;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.otmwjtcoc.persistence.HibernateUtil;

public class OneToManyWithJoinWithCollectionOfComponents {

  private static final Logger logger = LoggerFactory
      .getLogger(OneToManyWithJoinWithCollectionOfComponents.class);

  public static void main(String[] args) {
    OneToManyWithJoinWithCollectionOfComponents otmwjtcoc =
        new OneToManyWithJoinWithCollectionOfComponents();
    otmwjtcoc.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    User aUser = new User("John", "Doe", "johndoe", "oednhoj", "johndoe@hibernate.org", 1, true);
    Category aCategory = new Category("Category 1", new HashSet<CategorizedItem>());
    Item anItem = new Item("Item 1");

    session.save(aUser);
    session.save(aCategory);
    session.save(anItem);

    CategorizedItem aLink = new CategorizedItem(aUser, aCategory, anItem);
    aCategory.getCategorizedItems().add(aLink);
    // aCategory.getCategorizedItems().remove(aLink);

    // session.save(aLink);

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
    logger.debug("{} category(ies) found", categories.size());
    for (Category category : categories) {
      logger.debug(category.toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
