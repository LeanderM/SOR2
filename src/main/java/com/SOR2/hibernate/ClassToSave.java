package com.SOR2.hibernate;

import java.util.Date;

public class ClassToSave {

	private Long id;

	private String title;
	private Date date;

	// constructor MUST be empty!
	public ClassToSave() {
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
