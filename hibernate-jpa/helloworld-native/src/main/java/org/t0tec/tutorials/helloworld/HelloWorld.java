package org.t0tec.tutorials.helloworld;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;
import org.t0tec.tutorials.persistence.HibernateUtil;

public class HelloWorld {
	private static Long msgId = 0L;
	
	public static void main(String[] args) {
		firstUnitOfWork();

		secondUnitOfWork();

		thirdUnitOfWork();
		
		fourthUnitOfWork();
		
		// Shutting down the application
		HibernateUtil.shutdown();
	}

	private static void firstUnitOfWork() {
		// First unit of work
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		Message message = new Message("Hello World");
		msgId = (Long) session.save(message);
				
		tx.commit();
		session.close();
	}
	
	@SuppressWarnings("rawtypes")
	private static void secondUnitOfWork() {
		// Second unit of work
		Session newSession = HibernateUtil.getSessionFactory().openSession();
		Transaction newTransaction = newSession.beginTransaction();
			
		List messages = newSession.createQuery("from Message m order by m.text asc").list();
		
		System.out.println(messages.size() + " message(s) found:");

		for (Iterator iter = messages.iterator(); iter.hasNext();) {
			Message loadedMsg = (Message) iter.next();
			System.out.println(loadedMsg.getText());
		}
		
		newTransaction.commit();
		newSession.close();
	}

	private static void thirdUnitOfWork() {
		// Third unit of work
		Session thirdSession = HibernateUtil.getSessionFactory().openSession();
		Transaction thirdTransaction = thirdSession.beginTransaction();
		
		// msgId holds the identifier value of the first message
		Message message = (Message) thirdSession.get(Message.class, msgId);
		message.setText("Greetings Earthling");
		message.setNextMessage(new Message("Take me to your leader (please)"));
		
		thirdTransaction.commit();
		thirdSession.close();
	}
	
	@SuppressWarnings("rawtypes")
	private static void fourthUnitOfWork() {
		// Fourth unit of work
		Session fourthSession = HibernateUtil.getSessionFactory().openSession();
		Transaction fourthTransaction = fourthSession.beginTransaction();
		
		Statistics stats = HibernateUtil.getSessionFactory().getStatistics();
		stats.setStatisticsEnabled(true);
		
		List messages = fourthSession.createQuery("from Message m order by m.text asc").list();
		
		System.out.println(messages.size() + " message(s) found:");

		for (Iterator iter = messages.iterator(); iter.hasNext();) {
			Message loadedMsg = (Message) iter.next();
			System.out.println(loadedMsg.getText());
		}
		
		System.out.println("entity count: " + fourthSession.getStatistics().getEntityCount());
		
		stats.getSessionOpenCount();
		stats.logSummary();
		EntityStatistics messageStats = stats.getEntityStatistics("org.t0tec.tutorials.helloworld.Message");
		
		System.out.println("fetch count: " + messageStats.getFetchCount());
		System.out.println("load count: " + messageStats.getLoadCount());
		
		fourthTransaction.commit();
		fourthSession.close();
	}
}
