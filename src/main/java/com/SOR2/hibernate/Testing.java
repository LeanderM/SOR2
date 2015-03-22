package com.SOR2.hibernate;

import java.util.List;

public class Testing {

	public static void main(String[] args) {

		/*
		 * alle inputs zijn nu functioneel. als je wilt testen effe deze entry's
		 * uit de tabel halen. test test
		 */
		// System.out.println(HibernateMain.addAccountType("swag"));
		// System.out.println(HibernateMain.addID("admin", "mark",
		// "hallo world",
		// "niet mark"));
		// System.out.println(HibernateMain.addUser(29, "marker", "passworde"));
		// System.out.println(HibernateMain.addMessage("hallo worldjes", "mark",
		// "swagre"));
		// System.out.println(HibernateMain.addMessageRecipient("admin", 11));
		// users = 'mark'

		// de methodiek om een specifieke entry op te halen volgens criteria
		// standaarden
		List data = HibernateMain.getMailForAdmin("admin");

		for (Object object : data) {
			Messages looped = (Messages) object;
			System.out.println(looped.getMessage());
		}
	}
}
