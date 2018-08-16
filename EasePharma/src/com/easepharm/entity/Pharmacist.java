package com.easepharm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PHARM")
public class Pharmacist extends BaseEntity{	

	
	@Column(name = "REGISTRATION_NO")
	String registrationNo;
	
	@Column(name = "ADDRESS")
	String address;
	
	
	public Pharmacist() {
		
	}
	
	public Pharmacist(String name, String address, String primaryId) {
		this.name = name;
		this.address = address;
		this.registrationNo = primaryId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String phoneNo) {
		this.name = phoneNo;
	}
	
}
