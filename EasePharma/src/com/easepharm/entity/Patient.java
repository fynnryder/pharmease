package com.easepharm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PATIENT")
public class Patient extends BaseEntity{
	
	
	@Column(name = "ADDRESS")
	String address;
	
	@Column(name = "PHONE_NO")
	String phoneNo;
	
	public Patient() {
		
	}
	
	public Patient(String name, String address, String primaryId) {
		this.name = name;
		this.address = address;
		this.phoneNo = primaryId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	

}
