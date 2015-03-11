package com.SOR2.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateMain {

	private static SessionFactory factory;

	public static void main(String[] args) {
		try {
			factory = new AnnotationConfiguration().configure().
			// addPackage("com.xyz") //add package if used.
					addAnnotatedClass(Employee.class).buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
}
