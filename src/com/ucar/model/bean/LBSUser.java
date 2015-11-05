package com.ucar.model.bean;

import java.io.Serializable;

import org.springframework.stereotype.Component;

public class LBSUser implements Serializable{
	private static final long serialVersionUID = 1626826826828L;
	private String username;
	private String password;
	private String isAuthen;
	private String authtype;
	private String cities;
	private String timeStamp;
	private String signature;
	private String nonse;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIsAuthen() {
		return isAuthen;
	}
	public void setIsAuthen(String isAuthen) {
		this.isAuthen = isAuthen;
	}
	public String getAuthtype() {
		return authtype;
	}
	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}
	public String getCities() {
		return cities;
	}
	public void setCities(String cities) {
		this.cities = cities;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getNonse() {
		return nonse;
	}
	public void setNonse(String nonse) {
		this.nonse = nonse;
	}
}

