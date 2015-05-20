package com.SOR2.hibernate;

import java.util.List;
import java.util.UUID;

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

		/*
		 * HashMap<Integer, String> data = HibernateMain
		 * .getAllStatusVallidOrInvallid(true);
		 * 
		 * System.out.println("ayyy"); System.out.println(data.size());
		 * System.out.println(data.get(17));
		 * System.out.println("---------------------------");
		 */

		UUID test = UUID.randomUUID();

		HibernateThreadObject obj = new HibernateThreadObject();
		System.out.println(obj.getAllSendQueItems());
		/*
		 * obj.addSendQueItem(test, "hallo iedereen", "test", "test",
		 * "Belastingsdienst", 1, 15); obj.addSendQueItem(test,
		 * "hallo iedereen22222", "test", "test", "Belastingsdienst", 1, 3);
		 * obj.addValidationQueItem(test, "hallo iedereentoVAL", "test", "",
		 * "Belastingsdienst", 1); obj.addValidationQueItem(test,
		 * "hallo iedereen22222toVAL", "test", "test", "Belastingsdienst", 1);
		 */

		/*
		 * System.out.println(obj.getFirstSendQueItem().getMessage());
		 * obj.deleteFirstSendQueItem(); System.out.println("deleted");
		 * System.out.println(obj.getFirstSendQueItem().getMessage());
		 * 
		 * System.out.println(obj.getAllSendQueItems().size());
		 */
		/*
		 * List data = obj.getAllSendQueItems();
		 * 
		 * obj.deleteItemsFromDB(data);
		 */

		System.out.println(obj.getStatusWithStatus_ID(4));

		List dataUser = obj.checkLogin("jesse", "jesse");

		obj.addValidationQueItem(test, "hallo iedereentoVAL", "test", "",
				"Belastingsdienst", 1);

		System.out.println(obj.getValidationItemByUUID(UUID.fromString("")));

		/*
		 * System.out.println(HibernateMain.getStatusByUUID(test, true));
		 * 
		 * System.out.println(HibernateMain.checkUUIDExists(test, false));
		 * 
		 * System.out.println(HibernateMain.checkUUIDExists(UUID.randomUUID(),
		 * false));
		 */

		/*
		 * List progresList = HibernateMain.getProgressForMessage(0, true);
		 * 
		 * for (Object obj : progresList) { Progress looped = (Progress) obj;
		 * System.out.println(looped.getProgressMessage()); }
		 */

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
