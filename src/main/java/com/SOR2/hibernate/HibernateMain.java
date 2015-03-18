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
	private static Session openSession;
	private static Transaction trans;
	private static Integer id;

	@SuppressWarnings("deprecation")
	public static void initFactory() {
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
			initFactory();
		}
	}
	
	public static void initParams(){
		// reset all data
		id = null;
		openSession = null;
		trans = null;
		//open sessie
		openSession = factory.openSession();
		
	}

	// ////////////////////////////////////////////////////////////////////////////
	// adding a single row //
	// ////////////////////////////////////////////////////////////////////////////
	public static Integer addAccountType(String name) {

		checkFactoryExists();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Account_type type = new Account_type();
			type.setName(name);
			id = (Integer) openSession.save(type);
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return id;
	}

	public static int addID(String accountType, String firstName,
			String message, String message_recipients) {

		checkFactoryExists();
		initParams();
		try {
			trans = openSession.beginTransaction();
			ID type = new ID();
			type.setAccountType(accountType);
			type.setFirstName(firstName);
			type.setMessage(message);
			type.setMessage_recipients(message_recipients);
			id = (Integer) openSession.save(type);
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return id;
	}

	public static int addMessage(String message, String sender, String subject) {

		checkFactoryExists();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Messages type = new Messages();
			type.setMessage(message);
			type.setSender(sender);
			type.setSubject(subject);
			id = (Integer) openSession.save(type);
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return id;
	}

	public static int addMessageRecipient(String recId, int message_id) {

		checkFactoryExists();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Message_recipients type = new Message_recipients();
			type.setRecipient_id(recId);
			type.setMessage_id(message_id);
			id = (Integer) openSession.save(type);
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return id;
	}

	public static int addUser(int accountType, String password,
			String username) {

		checkFactoryExists();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Users type = new Users();

			type.setUsername(username);
			type.setPassword(password);
			type.setAccountType(accountType);

			id = (Integer) openSession.save(type);

			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
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
