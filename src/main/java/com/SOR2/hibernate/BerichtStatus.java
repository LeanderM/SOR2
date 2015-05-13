package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//Tabel account_type in database
@Entity
@Table(name = "status_bericht")
public class BerichtStatus {

	@Column(name = "status")
	private String status;

	@Id
	@GeneratedValue
	@Column(name = "status_ID")
	private int status_ID;

	/**
	 * Lege constructor
	 */
	public BerichtStatus() {

	}

	/**
	 * getter voor de statusID
	 * 
	 * @return
	 */
	public int getStatus_ID() {
		return status_ID;
	}

	/**
	 * setter voor de statusID
	 * 
	 * @param status_ID
	 */
	public void setStatus_ID(int status_ID) {
		this.status_ID = status_ID;
	}

	/**
	 * Getter voor de status
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Setter voor de status
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
