package org.t0tec.tutorials.mtma;

import java.util.HashSet;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.mtma.persistence.HibernateUtil;

public class ManyToMany {

  private static final Logger logger = LoggerFactory.getLogger(ManyToMany.class);

  public static void main(String[] args) {
    ManyToMany mtm = new ManyToMany();
    mtm.doWork();
  }

  public void doWork() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Category aCategory = new Category("Category Foo", new HashSet<Item>());
    Item anItem = new Item("Foo", new HashSet<Category>());

    session.save(aCategory);
    session.save(anItem);

    aCategory.getItems().add(anItem);
    anItem.getCategories().add(aCategory); // This is needed for bidirectional

    tx.commit();
    session.close();

    // second unit of work
    getAllCategories();

    logger.debug("Item categories: {}", anItem.getCategories());

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
      logger.debug(c.getItems().toString());
    }

    newTransaction.commit();
    newSession.close();
  }

  @SuppressWarnings({"unchecked"})
  public static <T> List<T> listAndCast(Query q) {
    return q.list();
  }
}
