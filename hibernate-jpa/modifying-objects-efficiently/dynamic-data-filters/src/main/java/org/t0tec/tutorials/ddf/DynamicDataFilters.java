package org.t0tec.tutorials.ddf;

import org.hibernate.Filter;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.ddf.persistence.HibernateUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DynamicDataFilters {

  private static final Logger logger = LoggerFactory.getLogger(DynamicDataFilters.class);

  private long johndoeId;
  private long suedoeId;
  private long generalCategoryId;

  private long computerId;

  public static void main(String[] args) {
    DynamicDataFilters ddf = new DynamicDataFilters();
    ddf.doFirstUnit();
    ddf.doSecondUnit();
    ddf.doThirdUnit();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  public void doFirstUnit() {
    // First unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    Category generalCategory = new Category("General");

    User johndoe = new User("John", "Doe", "johndoe", "eodnhoj", "johndoe@hibernate.org", 10, true);

    User suedoe = new User("Sue", "Doe", "suedoe", "eodeus", "suedoe@hibernate.org", 3, true);

    Item fooItem = new Item("Foo item");

    Item barItem = new Item("Bar item");

    Set<Item> generalItems = new HashSet<Item>();

    // set seller associations
    fooItem.setSeller(johndoe);
    barItem.setSeller(johndoe);

    johndoe.getItemsForSale().add(fooItem);
    johndoe.getItemsForSale().add(barItem);

    // set category associations
    generalItems.add(fooItem);
    generalItems.add(barItem);

    fooItem.getCategories().add(generalCategory);
    barItem.getCategories().add(generalCategory);

    johndoeId = (Long) session.save(johndoe);
    suedoeId = (Long) session.save(suedoe);
    generalCategoryId = (Long) session.save(generalCategory);
    session.save(fooItem);
    session.save(barItem);


    tx.commit();
    session.close();
  }

  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    User loggedInUser = (User) session.get(User.class, suedoeId);

    Filter filter = session.enableFilter("limitItemsByUserRank");
    filter.setParameter("currentUserRank", loggedInUser.getRanking());

    // List<Item> filteredItems = session.createQuery("from Item").list();

    List<Item> filteredItems = session.createCriteria(Item.class).list();

    if (filteredItems.isEmpty()) {
      logger.debug("You're not allowed to access these items (RANK TOO LOW!)");
    }

    for (Item filteredItem : filteredItems) {
      logger.debug(filteredItem.toString());
    }

    tx.commit();
    session.close();
  }

  public void doThirdUnit() {
    // Third unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    User loggedInUser = (User) session.get(User.class, johndoeId);

    Filter filter = session.enableFilter("limitItemsByUserRank");
    filter.setParameter("currentUserRank", loggedInUser.getRanking());

    List<Category> filteredItemsOfCategories = session.createCriteria(Category.class).list();

    logger.debug("{}",filteredItemsOfCategories.get(0).getName());

    logger.debug("{}", filteredItemsOfCategories.get(0).getItems().size());

    tx.commit();
    session.close();
  }

}
