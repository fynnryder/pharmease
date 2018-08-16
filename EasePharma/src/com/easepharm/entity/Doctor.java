package com.easepharm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DOCTOR")
public class Doctor extends BaseEntity{
	
	
	@Column(name = "LICENSE_NO")
	String licenceNo;
	
	@Column(name = "ADDRESS")
	String address;
	
	public Doctor() {
		
	}
	
	public Doctor(String name, String address, String primaryId) {
		this.address = address;
		this.name = name;
		this.licenceNo = primaryId;
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
	public String getLicenceNo() {
		return licenceNo;
	}
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
