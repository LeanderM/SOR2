package com.SOR2.hibernate;

import java.util.HashMap;
import java.util.List;

public class Testing {
	// tsdfvsdfg
	public static void main(String[] args) {

		/*
		 * alle inputs zijn nu functioneel. als je wilt testen effe deze entry's
		 * uit de tabel halen. test test
		 */
		// System.out.println(HibernateMain.addAccountType("swag"));
		// System.out.println(HibernateMain.addID("admin", "mark",
		// "hallo world",
		// "niet mark"));
		// HibernateMain.addUser(1, "pass", "jesse2");
		// HibernateMain.addUser(31, "pieter", "niet een echte passwwwwwww");
		// HibernateMain.addMessage("hallo lorum ipsum355555555577777", "mark",
		// "swagre", "admin");
		// HibernateMain.addMessage("hallo worldjess", "jesse", "swagre",
		// "mark");
		// HibernateMain
		// .addMessage("hallo worldjesss", "admin", "swagre", "jesse");
		// HibernateMain.addMessage("hallo worldjessss", "pieter", "swagre",
		// "jesse");
		// System.out.println(HibernateMain.addMessageRecipient("admin", 11));
		// users = 'mark'

		// de methodiek om een specifieke entry op te halen volgens criteria
		// standaarden
		// List data = HibernateMain.checkLogin("mark", "password");

		/*
		 * List var = HibernateMain.getAllInvallidMessages(); List varSender =
		 * HibernateMain
		 * .getInvallidMessagesForSpecificSenderOrReciever("admin", null);
		 * 
		 * for (Object obj : var) { InvallidMessage msg = (InvallidMessage) obj;
		 * System.out.println(msg.getMessage()); }
		 * 
		 * for (Object obj : varSender) { InvallidMessage msg =
		 * (InvallidMessage) obj; System.out.println(msg.getSender()); }
		 */

		// System.out.println(HibernateMain.checkUsrExists("test"));

		HashMap<Integer, String> data = HibernateMain
				.getAllStatusVallidOrInvallid(false);

		System.out.println("ayyy");
		System.out.println(data.size());
		System.out.println(data.get(17));
		System.out.println("---------------------------");

		List progresList = HibernateMain.getProgressForMessage(0, true);

		for (Object obj : progresList) {
			Progress looped = (Progress) obj;
			System.out.println(looped.getProgressMessage());
		}

		// System.out.println(HibernateMain.addProgress(0, "testprogressie",
		// true));
		// for (Object obj : data) {
		// UserConnectData loop = (UserConnectData) obj;
		// System.out.println(loop.getUsername());
		// }

		/*
		 * System.out.println(HibernateMain
		 * .checkUsrExists("markmjyfrkuyrkuyrfkuyf"));
		 * 
		 * for (Object object : data) { Users looped = (Users) object;
		 * System.out.println(looped.getPassword());
		 * System.out.println(looped.getUsername()); }
		 */

	}
}
