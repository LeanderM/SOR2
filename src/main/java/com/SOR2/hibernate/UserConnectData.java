package com.SOR2.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//Tabel in de database. test
@Entity
@Table(name = "userConnectData")
public class UserConnectData {

	@Id
	@Column(name = "Username")
	public String Username;

	@Column(name = "url")
	public String url;

	@Column(name = "namespace")
	public String namespace;

	@Column(name = "ServiceName")
	private String ServiceName;

	public UserConnectData() {

	}

	public String getUsername() {
		return Username;
	}

	public String getUrl() {
		return url;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setUsername(String usr) {
		this.Username = usr;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setNamespace(String space) {
		this.namespace = space;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}
}
