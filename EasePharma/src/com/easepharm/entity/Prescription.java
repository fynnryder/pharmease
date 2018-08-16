package com.easepharm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Prescription")
public class Prescription {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
	@Column(name = "USER_ID")
	long userId;
	
	@Column(name = "URI")
	String uri;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
