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
		// HibernateMain.addUser(30, "jesse", "niet een echte pass");
		// HibernateMain.addUser(31, "pieter", "niet een echte passwwwwwww");
		// HibernateMain.addMessage("hallo lorum ipsum3555555555", "mark",
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
		List data = HibernateMain.checkLogin("mark", "password");

		System.out.println(HibernateMain.checkUsrExists("mark"));

		for (Object object : data) {
			Users looped = (Users) object;
			System.out.println(looped.getPassword());
			System.out.println(looped.getUsername());
		}
	}
}
