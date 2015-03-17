package com.SOR2.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Testing {

	public static void main(String[] args) {

		// de tinyint moet verranderd worden in een int en de naam
		// messages.message_id mag geen . hebben
		// System.out.println(HibernateMain.addAccountType("testType"));
		// System.out.println(HibernateMain.addID("admin", "mark",
		// "hallo world",
		// "niet mark"));
		// System.out.println(HibernateMain.addUser(29, "marker", "passworde"));
		// System.out.println(HibernateMain.addMessage("hallo worldjes", "mark",
		// "swagre"));
		// System.out.println(HibernateMain.addMessageRecipient("admin", 11));

		ArrayList<String> colom = new ArrayList<String>();
		colom.add("messages");
		colom.add("users");
		colom.add("id");
		colom.add("message_recipients");

		List data = HibernateMain.getSpecificSelection(colom, "ID", "users",
				"'mark'");

		System.out.println(data);

		for (Object object : data) {
			Map row = (Map) object;
			System.out.print("my name: " + row.get("users"));
		}
	}
}
