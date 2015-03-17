package com.SOR2.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

public abstract class HibernateMain {

	private static SessionFactory factory;

	@SuppressWarnings("deprecation")
	public static void init() {
		try {
			factory = new AnnotationConfiguration().configure()
					.addAnnotatedClass(Account_type.class)
					.addAnnotatedClass(ID.class)
					.addAnnotatedClass(Message_recipients.class)
					.addAnnotatedClass(Messages.class)
					.addAnnotatedClass(Users.class).buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void checkFactoryExists() {
		if (factory == null) {
			init();
		}
	}

	// ////////////////////////////////////////////////////////////////////////////
	// adding a single row //
	// ////////////////////////////////////////////////////////////////////////////
	public static Integer addAccountType(String name) {

		checkFactoryExists();
		Session session = factory.openSession();
		Transaction tx = null;
		Integer id = null;
		try {
			tx = session.beginTransaction();
			Account_type type = new Account_type();
			type.setName(name);
			id = (Integer) session.save(type);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	public static int addID(String accountType, String firstName,
			String message, String message_recipients) {

		checkFactoryExists();
		Session session = factory.openSession();
		Integer id = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ID type = new ID();
			type.setAccountType(accountType);
			type.setFirstName(firstName);
			type.setMessage(message);
			type.setMessage_recipients(message_recipients);
			id = (Integer) session.save(type);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	public static int addMessage(String message, String sender, String subject) {

		checkFactoryExists();
		Session session = factory.openSession();
		Integer id = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Messages type = new Messages();
			type.setMessage(message);
			type.setSender(sender);
			type.setSubject(subject);
			id = (Integer) session.save(type);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	public static int addMessageRecipient(String recId, int message_id) {

		checkFactoryExists();
		Session session = factory.openSession();
		Integer id = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Message_recipients type = new Message_recipients();
			type.setRecipient_id(recId);
			type.setMessage_id(message_id);
			id = (Integer) session.save(type);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	public static String addUser(int accountType, String password,
			String username) {

		checkFactoryExists();

		Session session = factory.openSession();
		String id = null;
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Users type = new Users();
			type.setAccountType(accountType);
			type.setPassword(password);
			type.setUsername(username);

			id = (String) session.save(type);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;
	}

	// ////////////////////////////////////////////////////////////////////////////
	// getting a single object //
	// ////////////////////////////////////////////////////////////////////////////

	public static List getSpecificSelection(ArrayList<String> colom,
			String table, String wherePre, String whereAfter) {
		checkFactoryExists();
		Session session = factory.openSession();
		Transaction tx = null;
		List data = null;
		String colomList = "";

		for (String string : colom) {
			colomList += string + ", ";

		}
		colomList = colomList.substring(0, colomList.length() - 2);
		try {
			tx = session.beginTransaction();
			String sql = "SELECT " + colomList + " FROM " + table + " Where "
					+ wherePre + " = " + whereAfter;
			// System.out.println(sql);
			SQLQuery query = session.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			data = query.list();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return data;
	}

}
