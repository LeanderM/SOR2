package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Tabel in de database. test
@Entity
@Table(name = "userConnectData")
public class UserConnectData {

	public UserConnectData() {

	}

	@Id
	@Column(name = "Username")
	public String Username;

}
