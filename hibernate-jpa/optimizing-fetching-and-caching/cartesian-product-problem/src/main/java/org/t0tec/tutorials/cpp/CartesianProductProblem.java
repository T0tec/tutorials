package org.t0tec.tutorials.cpp;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.cpp.persistence.HibernateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartesianProductProblem {

  private static final Logger logger = LoggerFactory.getLogger(CartesianProductProblem.class);

  public static void main(String[] args) {
    CartesianProductProblem npop = new CartesianProductProblem();
    npop.doFirstUnit();
    npop.doSecondUnit();

    // Shutting down the application
    HibernateUtil.shutdown();
  }

  public void doFirstUnit() {
    // First unit of work
    GregorianCalendar gc = new GregorianCalendar();
    gc.setLenient(false);
    gc.set(2015, 0, 31, 14, 33, 48);

    Address johndoesAddress = new Address("Doe street", "90000", "Doe city");

    User johndoe = new User("John", "Doe", "johndoe", "eodnhoj", "johndoe@hibernate.org", 1, true);
    johndoe.setHomeAddress(johndoesAddress);

    User suedoe = new User("Sue", "Doe", "suedoe", "eodeus", "suedoe@hibernate.org", 2, false);

    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    session.save(johndoe);
    session.save(suedoe);

    for (int i = 0; i < 10; i++) {
      Item item = new Item("Item nr. " + i);
      Bid bid = new Bid(new BigDecimal(99 + i), new Date());
      Bid biggerBid = new Bid(new BigDecimal(99 + 2 * i), new Date());
      Image imageOne = new Image("foo.jpg", "foo.jpg", 400, 300);
      Image imageTwo = new Image("bar.jpg", "bar.jpg", 500, 300);

      if (i % 2 == 0) {
        logger.debug("{}", i % 2);
        item.setSeller(johndoe);
        bid.setItem(item);
        biggerBid.setItem(item);
        item.getImages().add(imageOne);
        item.getImages().add(imageTwo);
      } else {
        item.setSeller(suedoe);
        bid.setItem(item);
        biggerBid.setItem(item);
        item.getImages().add(imageTwo);
      }

      item.getBids().add(bid);
      item.getBids().add(biggerBid);

      session.save(item);
    }

    tx.commit();
    session.close();
  }

  // Problem:
  //  look at cartesian-product-problem.png:
  //  This resultset contains lots of redundant data. Item 1 has three bids and two
  //  images, item 2 has one bid and one image, and item 3 has no bids and no images.
  //  The size of the product depends on the size of the collections you’re retrieving: 3
  //  times 2, 1 times 1, plus 1, total 8 result rows. Now imagine that you have 1,000
  //  items in the database, and each item has 20 bids and 5 images—you’ll see a result-
  //  set with possibly 100,000 rows! The size of this result may well be several mega-
  //  bytes. Considerable processing time and memory are required on the database
  //  server to create this resultset. All the data must be transferred across the network.
  //  Hibernate immediately removes all the duplicates when it marshals the resultset
  //  into persistent objects and collections—redundant information is skipped. Three
  //  queries are certainly faster!
  //  You get three queries if you map the parallel collections with fetch="subse-
  //  lect" ; this is the recommended optimization for parallel collections. However,
  //      for every rule there is an exception. As long as the collections are small, a prod-
  //  uct may be an acceptable fetching strategy. Note that parallel single-valued asso-
  //  ciations that are eagerly fetched with outer-join SELECT s don’t create a product,
  //  by nature.
  //  Finally, although Hibernate lets you create Cartesian products with
  //  fetch="join" on two (or even more) parallel collections, it throws an excep-
  //  tion if you try to enable fetch="join" on parallel <bag> collections. The result-
  //  set of a product can’t be converted into bag collections, because Hibernate
  //  can’t know which rows contain duplicates that are valid (bags allow duplicates)
  //  and which aren’t. If you use bag collections (they are the default @OneToMany
  //      collection in Java Persistence), don’t enable a fetching strategy that results in
  //  products. Use subselects or immediate secondary-select fetching for parallel
  //  eager fetching of bag collections
  public void doSecondUnit() {
    // Second unit of work
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();

    List<Item> allItems = session.createCriteria(Item.class).list();

    logger.debug(allItems.toString());

    tx.commit();
    session.close();
  }

}
