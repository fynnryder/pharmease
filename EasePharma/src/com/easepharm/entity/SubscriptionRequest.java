package com.easepharm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SUBCRIPTION_REQUEST")
public class SubscriptionRequest {
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;
	
	@Column(name = "REQUESTER_ID")
	long requesterId;
	
	@Column(name = "USER_ID")
	long userId;
	
	@Column(name = "STATUS")
	int status;
	
	@Column(name = "USER_TYPE")
	String userType;
	
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public SubscriptionRequest() {
		
	}
	
	public SubscriptionRequest(Long requestId, String userId,String type) {
		
		this.requesterId = requestId;
		this.userId = Long.parseLong(userId);
		this.userType = type;
		status = 0;
		
	}
	public SubscriptionRequest(Long requestId, Long userId,String type) {
		this.requesterId = requestId;
		this.userId = userId;
		this.userType = type;
		status = 0;
	}
	
	public long getRequesterId() {
		return requesterId;
	}
	public void setRequesterId(long requesterId) {
		this.requesterId = requesterId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
