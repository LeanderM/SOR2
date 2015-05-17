package com.SOR2.hibernate;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//Tabel account_type in database
@Entity
@Table(name = "progress")
public class Progress {

	@Id
	@GeneratedValue
	@Column(name = "progress_id")
	private int status_ID;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "progressMessage")
	private String progressMessage;

	@Column(name = "vallid")
	private boolean vallid;

	@GeneratedValue
	@Column(name = "date")
	private Timestamp date;

	public int getStatus_ID() {
		return status_ID;
	}

	public void setStatus_ID(int status_ID) {
		this.status_ID = status_ID;
	}

	public String getUUID() {
		return uuid;
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

	public String getProgressMessage() {
		return progressMessage;
	}

	public void setProgressMessage(String progressMessage) {
		this.progressMessage = progressMessage;
	}

	public boolean isVallid() {
		return vallid;
	}

	public void setVallid(boolean vallid) {
		this.vallid = vallid;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
}
