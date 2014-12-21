package org.t0tec.tutorials.cmt;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.cmt.persistence.HibernateUtil;

public class CustomMapTypes {

    private static final Logger logger = LoggerFactory.getLogger(CustomMapTypes.class);

    public static void main(String[] args) {
        CustomMapTypes cmt = new CustomMapTypes();
//        cmt.doFirstUnit();
//        cmt.doSecondUnit();
        cmt.doThirdUnit();
    }

    public void doFirstUnit() {
        // First unit of work
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        MonetaryAmount maUSD = new MonetaryAmount(new BigDecimal(733.74), Currency.getInstance("USD"));
        MonetaryAmount maEUR = new MonetaryAmount(new BigDecimal(599.99), Currency.getInstance("EUR"));
        
        Item newItem = new Item("Red&Blue Dell Laptop", "15\" laptop with Intel Core i5", maUSD);
        
        Item sameItem = new Item("Blue&White Dell Laptop", "15\" laptop with Intel Core i5", MonetaryAmount.convert(maEUR, Currency.getInstance("USD")));
        
        session.save(newItem);
        session.save(sameItem);
        
        tx.commit();
        session.close();

        getAllItems();
        
        HibernateUtil.shutdown();
    }
    
    public void doSecondUnit() {
        // Second unit of work
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        MonetaryAmount maUSD = new MonetaryAmount(new BigDecimal(733.74), Currency.getInstance("USD"));
        MonetaryAmount maEUR = new MonetaryAmount(new BigDecimal(599.99), Currency.getInstance("EUR"));
     
        session.save(new Item("Red&Blue Dell Laptop", "15\" laptop with Intel Core i5", maUSD));
        session.save(new Item("Blue&White Dell Laptop", "15\" laptop with Intel Core i5", maEUR));
        session.save(new Item("Black&Yellow Dell Laptop", "15\" laptop with Intel Core i5", maEUR));
        session.save(new Item("Logitec USB Wireless Mouse", "Wireless USB mouse with Logitech Unifying software", new MonetaryAmount(new BigDecimal(50), Currency.getInstance("EUR"))));
        
        tx.commit();
        session.close();
        
        Session newSession = HibernateUtil.getSessionFactory().openSession();
        Transaction newTransaction = newSession.beginTransaction();
        
        List<Item> euroItems = listAndCast(newSession.createQuery("from Item i where i.initialPrice.value > 40 and i.initialPrice.currency = 'EUR' order by i.initialPrice.value asc"));
        logger.debug("{} euro item(s) found", euroItems.size());
        for (Item item : euroItems) {
            logger.debug(item.toString());
        }
        
        newTransaction.commit();
        newSession.close();
                
        HibernateUtil.shutdown();
    }
    

    public void doThirdUnit() {
        // Third unit of work
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        MonetaryAmount maUSD = new MonetaryAmount(new BigDecimal(733.74), Currency.getInstance("USD"));
        MonetaryAmount maEUR = new MonetaryAmount(new BigDecimal(599.99), Currency.getInstance("EUR"));
        
        Item newItem = new Item("Red&Blue Dell Laptop", "15\" laptop with Intel Core i5", maUSD);
        
        Item sameItem = new Item("Blue&White Dell Laptop", "15\" laptop with Intel Core i5", maEUR);
        
        session.save(newItem);
        session.save(sameItem);
        
        tx.commit();
        session.close();

        getAllItems();
        
        HibernateUtil.shutdown();
    }
    

	private void getAllItems() throws HibernateException {
		Session newSession = HibernateUtil.getSessionFactory().openSession();
		Transaction newTransaction = newSession.beginTransaction();

		List<Item> items = listAndCast(newSession
				.createQuery("from Item i order by i.initialPrice.value asc"));
		logger.debug("{} item(s) found", items.size());
		for (Item item : items) {
			logger.debug(item.toString());
		}

		newTransaction.commit();
		newSession.close();
	}

	@SuppressWarnings({ "unchecked" })
	public static <T> List<T> listAndCast(Query q) {
		return q.list();
	}
}
