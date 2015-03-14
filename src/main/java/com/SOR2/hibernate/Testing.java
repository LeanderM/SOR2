package com.SOR2.hibernate;

public class Testing {

	public static void main(String[] args) {
		 
		//de tinyint moet verranderd worden in een int en de naam messages.message_id mag geen . hebben 
		System.out.println(HibernateMain.addAccountType("testType"));
		System.out.println(HibernateMain.addID("admin", "mark", "hallo world", "niet mark"));
	//	System.out.println(HibernateMain.addUser(1,"passworde", "marker"));	
		System.out.println(HibernateMain.addMessage("hallo worldjes", "mark", "swagre"));
	//	System.out.println(HibernateMain.addMessageRecipient("admin", 11));

		
		
	}
}
