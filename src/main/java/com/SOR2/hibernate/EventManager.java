package com.SOR2.hibernate;

import java.util.Date;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hibernate.Session;

public class EventManager extends WebPage {

	public EventManager(final PageParameters parameters) {

		super(parameters);
		createAndStoreEvent("title!", new Date());

	}

	private void createAndStoreEvent(String title, Date theDate) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		ClassToSave theEvent = new ClassToSave();
		theEvent.setTitle(title);
		theEvent.setDate(theDate);
		session.save(theEvent);

		session.getTransaction().commit();
	}
}
