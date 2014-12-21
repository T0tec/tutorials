package org.t0tec.tutorials.ceh;

import java.util.List;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.t0tec.tutorials.ceh.persistence.HibernateUtil;
import org.t0tec.tutorials.ceh.persistence.StringEnumUserType;

public class CustomEnumHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomEnumHandler.class);

    public static void main(String[] args) {
        CustomEnumHandler ceh = new CustomEnumHandler();
        ceh.doWork();
    }

    public void doWork() {
        // First unit of work
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        Comment badComment = new Comment(Rating.BAD, "This isn't a good product!!!");
        Comment okComment = new Comment(Rating.OK, "This is a good product!");
        Comment excellentComment = new Comment(Rating.EXCELLENT, "This is an excellent product!!!!!");
        Comment secondBadComment = new Comment(Rating.BAD, "This is a bad product!!");
        
        session.save(badComment);
        session.save(okComment);
        session.save(secondBadComment);
        session.save(excellentComment);
        
        tx.commit();
        session.close();

        // Second unit of work
        getAllcomments();
        
        // Third unit of work
        Session secondSession = HibernateUtil.getSessionFactory().openSession();
        Transaction secondTx = secondSession.beginTransaction();
        
        List<Comment> badComments = listAndCast(secondSession.createQuery("from Comment c where c.rating = org.t0tec.tutorials.ceh.Rating.BAD"));
        for (Comment comment : badComments) {
            logger.debug(comment.toString());
        }
        
        secondTx.commit();
        secondSession.close();
        
        // Fourth unit of work
        Session thirdSession = HibernateUtil.getSessionFactory().openSession();
        Transaction thirdTx = thirdSession.beginTransaction();
        
		Query q = thirdSession.createQuery("from Comment c where c.rating = :rating");
		Properties params = new Properties();
		params.put("enumClassname", "org.t0tec.tutorials.ceh.Rating");
		q.setParameter("rating", Rating.OK, Hibernate.custom(StringEnumUserType.class, params));
		
        
		List<Comment> okComments = listAndCast(q);
        for (Comment comment : okComments) {
            logger.debug(comment.toString());
        }
        
        thirdTx.commit();
        thirdSession.close();
        
        // Fifth unit of work
     
            
        // Shutting down the application
        HibernateUtil.shutdown();
    }

    private void getAllcomments() throws HibernateException {
        Session newSession = HibernateUtil.getSessionFactory().openSession();
        Transaction newTransaction = newSession.beginTransaction();
        
        List<Comment> comments = listAndCast(newSession.createQuery("from Comment c order by c.rating asc"));
        logger.debug("{} comment(s) found", comments.size());
        for (Comment comment : comments) {
            logger.debug(comment.toString());
        }
        
        newTransaction.commit();
        newSession.close();
    }

    
    @SuppressWarnings({ "unchecked" })
	public static <T> List<T> listAndCast(Query q) {
        return q.list();
    }
}
