package model.util;

/*
 * HibernateUtil.java
 *
 * Created on August 7, 2006, 8:00 AM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	public void init() {
	}

	private static ThreadLocal session;

	//
	private static SessionFactory sessionFactory;
	private static final ThreadLocal transactionThread = new ThreadLocal();
	static {
		sessionFactory = new AnnotationConfiguration().configure(
				"hibernate.cfg.xml").buildSessionFactory();

		session = new ThreadLocal();
	}

	public static Session openSession() {
		return sessionFactory.openSession();
	}

	public static Session getCurrentSession() {
		if (session.get() == null) {
			session.set(openSession());
		}
		return (Session) session.get();
	}

	public static void closeCurrentSession() {

		session.set(null);
		if (session != null) {
			getCurrentSession().close();
		}

	}

	public static void beginTransaction() {
		if (transactionThread.get() == null) {
			Transaction transaction = getCurrentSession().beginTransaction();
			transactionThread.set(transaction);
		}
	}

	public static void commitTransaction() {
		Transaction transaction = (Transaction) transactionThread.get();
		if (transaction != null && !transaction.wasCommitted()
				&& !transaction.wasRolledBack()) {
			transaction.commit();
			transactionThread.set(null);
		}

		// closeCurrentSession();
	}

	public static void rollbackTransaction() {
		Transaction transaction = (Transaction) transactionThread.get();
		if (transaction != null && !transaction.wasCommitted()
				&& !transaction.wasRolledBack()) {
			transaction.rollback();
			transactionThread.set(null);
		}

		// closeCurrentSession();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}