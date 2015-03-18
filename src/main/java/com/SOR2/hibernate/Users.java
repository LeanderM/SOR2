package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
// Tabelnaam voor de database.
@Table(name = "users")
public class Users {

	@Id
	// kolomwaarde
	@Column(name = "username")
	// String username
	private String username;

	@Column(name = "UserPassword")
	// String password
	private String password;

	@Column(name = "accountType")
	// int met accounttype
	private int accountType;

	/**
	 * Contructor moet leeg zijn.
	 */
	public Users() {

	}

	/**
	 * Getter voor de username.
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter voor de username.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter voor Password.
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter voor password
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter voor accountType.
	 * 
	 * @return accountType
	 */
	public int getAccountType() {
		return accountType;
	}

	/**
	 * Setter voor accountType.
	 * 
	 * @param accountType
	 */
	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

}
