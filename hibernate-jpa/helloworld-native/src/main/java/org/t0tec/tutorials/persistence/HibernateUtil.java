package org.t0tec.tutorials.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.EntityStatistics;
import org.hibernate.stat.Statistics;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		// Alternatively, you could look up in JNDI here
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

	public static void getMessageStats() { // or use hibernate.generate_statistics = true in config
		Statistics stats = HibernateUtil.getSessionFactory().getStatistics();
		stats.setStatisticsEnabled(true);
		// ...
		stats.getSessionOpenCount();
		stats.logSummary();
		EntityStatistics messageStats = stats.getEntityStatistics("org.t0tec.tutorials.Message");
		messageStats.getFetchCount();
	}
}
