package com.SOR2.hibernate;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
import org.hibernate.exception.ConstraintViolationException;

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

	// bevat de response message voor feedback voor de inputs
	private static Integer id;

	private static int counter;

	/*
	 * instantieerd de hibernate factory en vult de variable van een factory met
	 * alle models gedefineert in de hibernate map
	 */
	@SuppressWarnings("deprecation")
	public static void initFactory() {
		try {
			factory = new AnnotationConfiguration().configure()
					.addAnnotatedClass(Account_type.class)
					.addAnnotatedClass(Messages.class)
					.addAnnotatedClass(InvallidMessage.class)
					.addAnnotatedClass(BerichtStatus.class)
					.addAnnotatedClass(UserConnectData.class)
					.addAnnotatedClass(Progress.class)
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
	public static void checkFactoryExistsElseInit() {
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

		checkFactoryExistsElseInit();
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
	 * voegt een message toe aan de databse
	 *
	 */
	public static int addMessage(UUID uuid, String message, String sender,
			String subject, String receiver, int status) {

		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Messages type = new Messages();
			type.setUuid(uuid.toString());
			type.setMessage(message);
			type.setSender(sender);
			type.setSubject(subject);
			type.setReceiver(receiver);
			type.setStatus(status);

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

	/*
	 * voegt een invalid message toe aan de database
	 */
	public static int addInvallidMessage(UUID uuid, String message,
			String sender, String subject, String receiver, int status) {

		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			InvallidMessage type = new InvallidMessage();
			type.setUuid(uuid.toString());
			type.setMessage(message);
			type.setSender(sender);
			type.setSubject(subject);
			type.setReceiver(receiver);
			type.setStatus(status);

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
	 * voegt een status toe aan de databse
	 *
	 */
	public static void addStatus(String status) {

		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			BerichtStatus type = new BerichtStatus();
			type.setStatus(status);
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

	/**
	 * voegt een user toe aan de databse
	 *
	 */
	public static void addUser(int accountType, String password, String username) {

		checkFactoryExistsElseInit();
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

	/*
	 * voegt een progressiemoment toe aan de database dat bij een message hoord.
	 * Het is belangrijk dat er gecontroleerd wordt of de message vallid is of
	 * niet.
	 */
	public static int addProgress(String uuid, String progressMsg,
			boolean vallid) {

		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Progress type = new Progress();

			type.setUUID(uuid);
			type.setProgressMessage(progressMsg);
			type.setVallid(vallid);
			id = (Integer) openSession.save(type);
			openSession.save(type);

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

	public static int addValidationQueItem(UUID uuid, String message,
			String sender, String subject, String receiver, int status)
			throws ConstraintViolationException {

		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			ValidationQueItem type = new ValidationQueItem();
			type.setUuid(uuid.toString());
			type.setMessage(message);
			type.setSender(sender);
			type.setSubject(subject);
			type.setReceiver(receiver);
			type.setStatus(status);

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
	// Met de volgende methoden
	//
	//
	//
	//
	// getting a single object wanneer je geen where clause wilt meegeven aan de
	// query dan moet je 1=1 gebruiken
	// Met deze methode is het mogelijk om binnen de hibernate omgeving te
	// werken
	// met specifieke sql code die de HibernateMain klasse nog niet ondervangt.
	/*
	 * @param Strings die voor voor where clauses nodig zijn
	 * 
	 * @throws HibernateException
	 * 
	 * @returns Resultaat van de berekening.
	 */
	// ////////////////////////////////////////////////////////////////////////////
	public static List getSpecificSelectionRawSQL(String colom, String table,
			String otherSQL) {
		checkFactoryExistsElseInit();
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

	/**
	 * 
	 * @param usr
	 *            de usr vaarvoor de messages opgehaald voor moeten worden
	 * @return een lijst met messegas specifiek aan de megegeven user
	 */
	public static List getMailForAdmin(String usr) {
		checkFactoryExistsElseInit();
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

	/**
	 * 
	 * @param sender
	 *            stelt een sender voor die moet bestaan in de db
	 * @param receiver
	 *            stelt een receiver voor die moet bestaan in de db
	 * @return een lijst met invallid messages afhankelijk van de uitkomst van
	 *         de where
	 */
	public static List getInvallidMessagesForSpecificSenderOrReciever(
			String sender, String receiver) {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			if (sender != null) {
				data = openSession.createCriteria(InvallidMessage.class)
						.add(Restrictions.eq("sender", sender))
						.addOrder(Order.desc("date")).list();
			} else {
				data = openSession.createCriteria(InvallidMessage.class)
						.add(Restrictions.eq("receiver", receiver))
						.addOrder(Order.desc("date")).list();
			}
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

	/**
	 * 
	 * @return alle invalide berichten die op het moment in de database staan
	 */
	public static List getAllInvallidMessages() {
		checkFactoryExistsElseInit();
		initParams();

		try {

			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(InvallidMessage.class)
					.addOrder(Order.desc("date"));
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

	/**
	 * 
	 * @return alle statussen die in de database staan.
	 */
	public static List getAllStatussen() {
		checkFactoryExistsElseInit();
		initParams();

		try {

			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(BerichtStatus.class)
					.addOrder(Order.desc("date"));
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

	/**
	 * 
	 * @return alle valide berichten die op het moment in de database staan.
	 */
	public static List getAllMail() {
		checkFactoryExistsElseInit();
		initParams();

		try {

			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(Messages.class)
					.addOrder(Order.desc("date"));
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

	/**
	 * 
	 * Deze methode geeft het hele User object terug in een lijst waar ook nog
	 * extra controle op uitgevoerd kan worden. Bijv is deze user een admin of
	 * niet (accounttype)
	 * 
	 * @param usr
	 *            de gebruikersnaam die ingevoerd is
	 * @param pass
	 *            de password die ingevoerd is
	 * @return een lijst met de gespecificeerde usr als hij zou bestaan anders
	 *         een lege lijst test
	 */
	public static List checkLogin(String usr, String pass) {
		checkFactoryExistsElseInit();
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

	/**
	 * 
	 * Deze methode dient ervoor om te kijken of een gebruiker wel bestaat of
	 * niet
	 * 
	 * @param usr
	 *            de gebruiker die gecontroleerd dient te worden
	 * @return een boolean of de user bestaat of niet
	 */
	public static boolean checkUsrExists(String usr) {
		checkFactoryExistsElseInit();
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

	/**
	 * 
	 * Deze methode dient ervoor om te kijken welk accountype de megegeven
	 * gebruiker heeft
	 * 
	 * @param usr
	 *            de gebruiker die gecontroleerd dient te worden
	 * @return de naam van de desbetreffende accounttype
	 */
	public static String getUserTypeForAccount(String usr) {
		Account_type singleType = null;
		Integer specified = null;
		checkFactoryExistsElseInit();
		initParams();
		try {

			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(Users.class);
			crit.add(Restrictions.eq("username", usr));

			data = crit.list();
			trans.commit();

			for (Object object : data) {
				Users looped = (Users) object;
				specified = looped.getAccountType();
			}

			initParams();
			factory = null;
			checkFactoryExistsElseInit();

			trans = openSession.beginTransaction();
			Criteria critTwee = openSession.createCriteria(Account_type.class);
			critTwee.add(Restrictions.eq("id", specified));
			List resultset = critTwee.list();
			trans.commit();

			for (Object objectType : resultset) {
				Account_type typeLooped = (Account_type) objectType;

				singleType = typeLooped;
			}

		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}

		if (singleType == null) {
			return "dit account bestaat niet";
		}
		return singleType.getName();
	}

	/**
	 * Join om status te koppelen aan de Message.
	 * 
	 * @param message_ID
	 * @return de status
	 */
	public static String getStatusByMessage_ID(int message_ID) {
		String status = null;
		Integer status_ID = null;
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(Messages.class);
			crit.add(Restrictions.eq("message_ID", message_ID));

			data = crit.list();
			trans.commit();

			for (Object obj : data) {
				Messages loop = (Messages) obj;
				status_ID = loop.getStatus();
			}

			initParams();
			factory = null;
			checkFactoryExistsElseInit();

			trans = openSession.beginTransaction();
			Criteria critTwee = openSession.createCriteria(BerichtStatus.class);
			critTwee.add(Restrictions.eq("status_ID", status_ID));
			List resultset = critTwee.list();
			trans.commit();

			for (Object obj1 : resultset) {
				BerichtStatus loop1 = (BerichtStatus) obj1;
				status = loop1.getStatus();
			}
		}

		catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}

		return status;
	}

	/**
	 * Join om status te koppelen aan de InvallidMessage.
	 * 
	 * @param invallidMessage_ID
	 * @return de status
	 */
	public static String getStatusByInvallidMessage_ID(int invallidMessage_ID) {
		String status = null;
		Integer status_ID = null;
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(InvallidMessage.class);
			crit.add(Restrictions.eq("invallidMessage_ID", invallidMessage_ID));

			data = crit.list();
			trans.commit();

			for (Object obj : data) {
				InvallidMessage loop = (InvallidMessage) obj;
				status_ID = loop.getStatus();
			}

			initParams();
			factory = null;
			checkFactoryExistsElseInit();

			trans = openSession.beginTransaction();
			Criteria critTwee = openSession.createCriteria(BerichtStatus.class);
			critTwee.add(Restrictions.eq("status_ID", status_ID));
			List resultset = critTwee.list();
			trans.commit();

			for (Object obj1 : resultset) {
				BerichtStatus loop1 = (BerichtStatus) obj1;
				status = loop1.getStatus();
			}
		}

		catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}

		return status;
	}

	/**
	 * 
	 * Deze methode dient ervoor om te kijken welke status bij de int hoort.
	 * 
	 * @param id
	 *            het id van de status die opgehaalt moet gaan worden
	 * @return de status als String
	 */
	public static String getStatusWithStatus_ID(int id) {
		checkFactoryExistsElseInit();
		initParams();
		String status = null;
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(BerichtStatus.class);
			crit.add(Restrictions.eq("status_ID", id));
			List results = crit.list();
			data = crit.list();
			if (data == null) {
				trans.commit();
				return "deze status bestaat niet";
			}
			for (Object obj : data) {
				BerichtStatus looped = (BerichtStatus) obj;
				status = looped.getStatus();
			}
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return status;
	}

	/**
	 * 
	 * Deze methode dient ervoor om alle bestaande statusen in 1 keer op te
	 * halen om de huidige implementatie van de tabel te versnellen.
	 * 
	 * @param valid
	 *            een boolean waarmee gekeken kan worden of de messages valid
	 *            zijn of niet
	 * @return de hasmap met alle valide of invalide statusen
	 */
	public static HashMap<Integer, String> getAllStatusVallidOrInvallid(
			boolean vallid) {
		checkFactoryExistsElseInit();
		initParams();
		if (vallid) {
			try {
				trans = openSession.beginTransaction();
				Criteria crit = openSession.createCriteria(Messages.class);
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
			HashMap<Integer, String> map = new HashMap<Integer, String>();
			for (Object obj : data) {
				Messages looped = (Messages) obj;
				map.put(looped.getMessage_ID(),
						getStatusByMessage_ID(looped.getMessage_ID()));
			}
			return map;
		} else {
			try {
				trans = openSession.beginTransaction();
				Criteria crit = openSession
						.createCriteria(InvallidMessage.class);
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
			HashMap<Integer, String> map = new HashMap<Integer, String>();
			for (Object obj : data) {
				InvallidMessage looped = (InvallidMessage) obj;
				map.put(looped.getInvallidMessage_ID(),
						getStatusByInvallidMessage_ID(looped
								.getInvallidMessage_ID()));
			}
			return map;
		}
	}

	// pieters lazyloading tot nu toe
	//

	public static List getLazyMessages() {
		checkFactoryExistsElseInit();
		initParams();
		counter = 0;

		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(Messages.class);
			crit.setMaxResults(2);
			crit.setFirstResult(counter);
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
		counter = counter + 2;
		return data;
	}

	/**
	 * 
	 * Deze methode dient ervoor om te kijken welke url en namespace deze user
	 * heeft. f
	 * 
	 * @param usr
	 *            de naam die gechekt moet gaan worden
	 * 
	 * @return de response van de database
	 */
	public static List getUserNamespaceAndUrl(String usr) {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(UserConnectData.class);
			crit.add(Restrictions.eq("Username", usr));
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

	/**
	 * 
	 * @param message_id
	 *            de id waarvoor het progressiebericht wordt opgehaald vallid is
	 *            een boolean waarvoor wordt gekeken of het hier om een valide
	 *            bericht gaat of een invallid bericht.
	 * 
	 * @return een lijst met messegas specifiek aan de megegeven user
	 */
	public static List getProgressForMessage(UUID uuid, boolean vallid) {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(Progress.class);
			crit.add(Restrictions.eq("vallid", vallid));
			crit.add(Restrictions.eq("uuid", uuid.toString())).addOrder(
					Order.desc("date"));
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

	// test

	public static boolean checkMessage_idExists(int message_id, boolean vallid) {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			if (vallid) {
				Criteria crit = openSession.createCriteria(Messages.class);
				crit.add(Restrictions.eq("message_ID", message_id));
				data = crit.list();
			} else {
				Criteria crit = openSession
						.createCriteria(InvallidMessage.class);
				crit.add(Restrictions.eq("invallidMessage_ID", message_id));
				data = crit.list();
			}
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		if (data.size() != 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param UUID
	 *            een uuid waarmee gebruikers een status kunnen ophalen
	 * @param vallid
	 *            is een boolean waarvoor wordt gekeken of het hier om een
	 *            valide bericht gaat of een invallid bericht.
	 * 
	 * @return een lijst met messegas specifiek aan de megegeven user
	 */
	public static String getStatusByUUID(UUID uuid, boolean vallid) {
		checkFactoryExistsElseInit();
		initParams();
		String status = null;
		List initialData = null;
		try {
			trans = openSession.beginTransaction();
			if (vallid) {
				Criteria crit = openSession.createCriteria(Messages.class);
				crit.add(Restrictions.eq("uuid", uuid.toString()));
				initialData = crit.list();
			} else {
				Criteria crit = openSession
						.createCriteria(InvallidMessage.class);
				crit.add(Restrictions.eq("uuid", uuid.toString()));
				initialData = crit.list();
			}
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}

		if (vallid) {
			for (Object obj : initialData) {
				Messages loop = (Messages) obj;
				status = getStatusWithStatus_ID(loop.getStatus());
			}
		} else {
			for (Object obj : initialData) {
				InvallidMessage loop = (InvallidMessage) obj;
				status = getStatusWithStatus_ID(loop.getStatus());
			}
		}
		return status;
	}

	public static boolean checkUUIDExists(UUID uuid, boolean vallid) {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			if (vallid) {
				Criteria crit = openSession.createCriteria(Messages.class);
				crit.add(Restrictions.eq("uuid", uuid.toString()));
				data = crit.list();
			} else {
				Criteria crit = openSession
						.createCriteria(InvallidMessage.class);
				crit.add(Restrictions.eq("uuid", uuid.toString()));
				data = crit.list();
			}
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		if (data.size() != 0) {
			return true;
		}
		return false;
	}

}