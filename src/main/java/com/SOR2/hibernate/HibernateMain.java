package com.SOR2.hibernate;

import java.io.FileNotFoundException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
// ervoor gekozen om een annotatationmethode te gebruiken om op een visueel begrijpbare manier de klassen aan de factory 
// toe te kunnen voegen
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Met behulp van deze facade klasse is het mogelijk om methoden aan te spreken
 * om te interacteren met de database. In deze klassen bevinden zich ook
 * standaarmethoden die dit proces asisteren.
 *
 * @author Mark Dingemanse
 * @version 0.1.0
 *
 */
public abstract class HibernateMain {

	// bevat de factory zelf
	private static SessionFactory factory;
	// bevat de huidige sessie met de database. Bij de start van een nieuwe
	// methode word deze gereset
	private static Session openSession;
	// bevat informatie om hetgene over te zetten naar de db of om gegevens
	// eruit te halen
	private static Transaction trans;

	// bevat de lijst met return data
	private static List data;

	// bevat de response message voor feedback voor de puts
	private static Integer id;

	/*
	 * instantieerd de hibernate factory en vult de variable van een factory
	 */
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

	/**
	 * checkt of de factory al geinstantieerd is als dit niet het geval is wordt
	 * dat alsnog gedaan
	 */
	public static void checkFactoryExists() {
		if (factory == null) {
			initFactory();
		}
	}

	/**
	 * schoont en initieerd de parameters die over de verschillende methoden
	 * gebruikt worden.
	 *
	 * @param value1
	 *            Eerste waarde voor de berekening
	 *
	 * @throws FileNotFoundException
	 *
	 * @returns Resultaat van de berekening.
	 */
	public static void initParams() {
		// reset all data
		id = null;
		openSession = null;
		trans = null;
		data = null;
		// open sessie
		openSession = factory.openSession();

	}

	// ////////////////////////////////////////////////////////////////////////////
	// adding a single row //
	//
	/*
	 * @param Strings and ints afhankelijk van de kolommen van de Database
	 * 
	 * @catch HibernateException
	 * 
	 * @returns Een id waarmee kan gekeken worden of de methode succesvol is
	 * uitgevoerd //
	 * ////////////////////////////////////////////////////////////
	 * ////////////////
	 * 
	 * /** voegt een accountype toe aan de databse
	 */
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

	/**
	 * voegt een ID toe aan de databse
	 *
	 */
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

	/**
	 * voegt een message toe aan de databse
	 *
	 */
	public static int addMessage(String message, String sender, String subject,
			String receiver) {

		checkFactoryExists();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Messages type = new Messages();
			type.setMessage(message);
			type.setSender(sender);
			type.setSubject(subject);
			type.setReceiver(receiver);
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

	/**
	 * voegt een message receipient toe aan de databse
	 *
	 */
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

	/**
	 * voegt een user toe aan de databse
	 *
	 */
	public static void addUser(int accountType, String password, String username) {

		checkFactoryExists();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Users type = new Users();

			type.setUsername(username);
			type.setPassword(password);
			type.setAccountType(accountType);

			openSession.save(type);

			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
	}

	// ////////////////////////////////////////////////////////////////////////////
	// getting a single object wanneer je geen where clause wilt meegeven aan de
	// query dan moet je 1=1 gebruiken
	/*
	 * @param Strings die voor
	 * 
	 * @throws HibernateException
	 * 
	 * @returns Resultaat van de berekening.
	 */
	// ////////////////////////////////////////////////////////////////////////////

	public static List getSpecificSelectionRawSQL(String colom, String table,
			String otherSQL) {
		checkFactoryExists();
		initParams();

		try {
			trans = openSession.beginTransaction();

			String sql = "SELECT " + colom + " FROM " + table + " WHERE "
					+ otherSQL;
			SQLQuery query = openSession.createSQLQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			data = query.list();

			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}

		return data;
	}

	public static List getMailForAdmin(String usr) {
		checkFactoryExists();
		initParams();

		try {

			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(Messages.class);
			crit.add(Restrictions.eq("receiver", usr)).addOrder(
					Order.desc("date"));
			List results = crit.list();

			data = crit.list();
			trans.commit();

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}

		return data;
	}

	public static List getAllMail() {
		checkFactoryExists();
		initParams();

		try {

			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(Messages.class)
					.addOrder(Order.desc("date"));
			List results = crit.list();

			data = crit.list();
			trans.commit();

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}

		return data;
	}

	public static List checkLogin(String usr, String pass) {
		checkFactoryExists();
		initParams();

		try {

			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(Users.class);
			crit.add(Restrictions.eq("username", usr));
			crit.add(Restrictions.eq("password", pass));
			List results = crit.list();

			data = crit.list();
			trans.commit();

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}

		return data;
	}

	public static boolean checkUsrExists(String usr) {
		checkFactoryExists();
		initParams();

		try {

			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(Users.class);
			crit.add(Restrictions.eq("username", usr));
			List results = crit.list();

			data = crit.list();
			trans.commit();

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}

		for (Object object : data) {
			Users looped = (Users) object;
			if (looped.getUsername().equals(usr)) {
				return true;
			}
		}

		return false;

	}

}
