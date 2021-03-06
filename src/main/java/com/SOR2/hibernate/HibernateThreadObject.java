package com.SOR2.hibernate;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

public class HibernateThreadObject {

	// bevat de factory zelf
	private SessionFactory factory;
	// bevat de huidige sessie met de database. Bij de start van een nieuwe
	// methode word deze gereset
	private Session openSession;
	// bevat informatie om hetgene over te zetten naar de db of om gegevens
	// eruit te halen
	private Transaction trans;

	// bevat de lijst met return data
	private List data;

	// bevat de response message voor feedback voor de inputs
	private Integer id;

	private int counter;

	public HibernateThreadObject() {
		initFactory();
	}

	/*
	 * instantieerd de hibernate factory en vult de variable van een factory met
	 * alle models gedefineert in de hibernate map
	 */
	@SuppressWarnings("deprecation")
	private void initFactory() {
		try {
			factory = new AnnotationConfiguration().configure()
					.addAnnotatedClass(SendQueItem.class)
					.addAnnotatedClass(ValidationQueItem.class)
					.addAnnotatedClass(Messages.class)
					.addAnnotatedClass(InvallidMessage.class)
					.addAnnotatedClass(BerichtStatus.class)
					.addAnnotatedClass(Users.class)
					.addAnnotatedClass(Progress.class).buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * checkt of de factory al geinstantieerd is als dit niet het geval is wordt
	 * dat alsnog gedaan
	 */
	private void checkFactoryExistsElseInit() {
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
	private void initParams() {
		// reset all data
		id = null;
		openSession = null;
		trans = null;
		data = null;
		// open sessie
		openSession = factory.openSession();

	}

	// populeer de 2 nieuwe tabellen

	public int addSendQueItem(UUID uuid, String message, String sender,
			String subject, String receiver, int status)
			throws ConstraintViolationException {

		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			SendQueItem type = new SendQueItem();
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

	public int addValidationQueItem(UUID uuid, String message, String sender,
			String subject, String receiver, int status)
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

	/**
	 * voegt een message toe aan de databse
	 *
	 */
	public int addMessage(UUID uuid, String message, String sender,
			String subject, String receiver, int status)
			throws ConstraintViolationException {

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
	public int addInvallidMessage(UUID uuid, String message, String sender,
			String subject, String receiver, int status)
			throws ConstraintViolationException {

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

	/*
	 * voegt een progressiemoment toe aan de database dat bij een message hoord.
	 * Het is belangrijk dat er gecontroleerd wordt of de message vallid is of
	 * niet.
	 */
	public int addProgress(String uuid, String progressMsg, boolean vallid) {

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

	// alle getters moet nog comments maken

	public boolean sendQueHasItems() {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(SendQueItem.class);
			data = crit.list();
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return (data.size() != 0) ? true : false;
	}

	public boolean validationQueHasItems() {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(ValidationQueItem.class);
			data = crit.list();
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return (data.size() != 0) ? true : false;
	}

	public SendQueItem getFirstSendQueItem() {
		checkFactoryExistsElseInit();
		initParams();
		SendQueItem item = null;
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(SendQueItem.class);
			crit.setFirstResult(0);
			crit.setMaxResults(1);
			data = crit.list();
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return (SendQueItem) data.get(0);
	}

	public ValidationQueItem getFirstValidationQueItem() {
		checkFactoryExistsElseInit();
		initParams();
		SendQueItem item = null;
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(ValidationQueItem.class);
			crit.setFirstResult(0);
			crit.setMaxResults(1);
			data = crit.list();
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		return (ValidationQueItem) data.get(0);
	}

	public void deleteFirstSendQueItem() {
		SendQueItem toDell = getFirstSendQueItem();
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			openSession.delete(toDell);
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
	}

	public void deleteFirstValidationQueItem() {
		ValidationQueItem toDell = getFirstValidationQueItem();
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			openSession.delete(toDell);
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}

	}

	public List getAllValidationItems() {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(ValidationQueItem.class);
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

	public List getAllSendQueItems() {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(SendQueItem.class);
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

	public void deleteItemsFromDB(List toDel) {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			for (Object obj : toDel) {
				openSession.delete(obj);
			}
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
	 * 
	 * Deze methode dient ervoor om te kijken welke status bij de int hoort.
	 * 
	 * @param id
	 *            het id van de status die opgehaalt moet gaan worden
	 * @return de status als String asdf
	 */
	public String getStatusWithStatus_ID(int id) {
		checkFactoryExistsElseInit();
		initParams();
		String status = null;
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(BerichtStatus.class);
			crit.add(Restrictions.eq("status_ID", id));
			data = crit.list();
			if (data == null) {
				trans.commit();
				return "deze status bestaat niet";
			}

			BerichtStatus looped = (BerichtStatus) data.get(0);
			status = looped.getStatus();

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

	public boolean checkUUIDExistsInMessageOrInvallid(UUID uuid, boolean vallid) {
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

	public boolean checkUUIDExistsInValidationQue(UUID uuid) {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(ValidationQueItem.class);
			crit.add(Restrictions.eq("uuid", uuid.toString()));
			data = crit.list();
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
	 * Deze methode geeft het hele User object terug in een lijst waar ook nog
	 * extra controle op uitgevoerd kan worden. Bijv is deze user een admin of
	 * niet (accounttype)
	 * 
	 * @param usr
	 *            de gebruikersnaam die ingevoerd is
	 * @param pass
	 *            de password die ingevoerd is
	 * @return een lijst met de gespecificeerde usr als hij zou bestaan anders
	 *         een lege lijst test test
	 */
	public List checkLogin(String usr, String pass) {
		checkFactoryExistsElseInit();
		initParams();

		try {

			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(Users.class);
			crit.add(Restrictions.eq("username", usr));
			crit.add(Restrictions.eq("password", pass));
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
	 * @param UUID
	 *            een uuid waarmee gebruikers een status kunnen ophalen
	 * @param vallid
	 *            is een boolean waarvoor wordt gekeken of het hier om een
	 *            valide bericht gaat of een invallid bericht.
	 * 
	 * @return een lijst met messegas specifiek aan de megegeven user
	 */
	public String getStatusByUUID(UUID uuid, boolean vallid) {
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
	
	public ValidationQueItem getValidationItemByUUID(UUID uuid) {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			Criteria crit = openSession.createCriteria(ValidationQueItem.class);
			crit.add(Restrictions.eq("uuid", uuid.toString()));
			data = crit.list();
			trans.commit();
		} catch (HibernateException e) {
			if (trans != null)
				trans.rollback();
			e.printStackTrace();
		} finally {
			openSession.close();
		}
		
		if(data.size() != 0){
			return (ValidationQueItem)data.get(0);
		}
		else{
			return null;
		}
		
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
	public  List getMessageForUUID(
			UUID uuid, boolean vallid) {
		checkFactoryExistsElseInit();
		initParams();
		try {
			trans = openSession.beginTransaction();
			if (vallid) {
				Criteria crit = openSession.createCriteria(Messages.class);
				crit.add(Restrictions.eq("uuid", uuid.toString()));
				data = crit.list();
			} else {
				Criteria crit = openSession.createCriteria(InvallidMessage.class);
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
		return data;
	}	
	
	
	
	
	
	

}
