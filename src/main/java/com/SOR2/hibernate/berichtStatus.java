package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//Tabel account_type in database
@Entity
@Table(name = "status_bericht")
public class berichtStatus {

	@Column(name = "verstuurd")
	private int verstuurd;

	@Column(name = "niet_verstuurd")
	private int niet_verstuurd;

	/**
	 * Lege constructor
	 */
	public berichtStatus() {

	}

	/**
	 * getter voor verstuurd
	 * 
	 * @return verstuurd
	 */
	public int getVerstuurd() {
		return verstuurd;
	}

	/**
	 * setter voor verstuurd
	 * 
	 * @param verstuurd
	 */
	public void setVerstuurd(int verstuurd) {
		this.verstuurd = 1;
	}

	/**
	 * getter voor niet_verstuurd
	 * 
	 * @return
	 */
	public int getNiet_Verstuurd() {
		return niet_verstuurd;
	}

	/**
	 * setter voor niet_verstuurd
	 * 
	 * @param niet_verstuurd
	 */
	public void setNiet_Verstuurd(int niet_verstuurd) {
		this.niet_verstuurd = 2;
	}
}
