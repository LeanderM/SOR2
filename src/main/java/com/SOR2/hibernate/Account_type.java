package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

// Tabel account_type in database
@Entity
@Table(name = "account_type")
public class Account_type {

	// Veld voor het ID
	// Kolomnaam id in database
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	// Veld voor de naam
	// Kolomnaam name in database
	@Column(name = "name")
	private String name;

	/**
	 * Constructor moet leeg zijn
	 */
	public Account_type() {

	}

	/**
	 * Getter voor het id
	 * 
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter voor id
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter voor de naam
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter voor de name
	 * 
	 * @param first_name
	 */
	public void setName(String first_name) {
		this.name = first_name;
	}

}
