package com.SOR2.hibernate;

import java.util.List;
import java.util.Map;

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

		String colom = "users";
		String where = "users = 'mark'";

		// de methodiek om een specifieke entry op te halen volgens hql
		// standaarden
		List data = HibernateMain.getSpecificSelection(colom, "ID", where);

		System.out.println(data);

		for (Object object : data) {
			Map row = (Map) object;
			System.out.print("my name: " + row.get("users"));
		}
	}
}
