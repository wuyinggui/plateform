package com.ucar.model;

import java.io.Serializable;
import java.util.Date;

/**序列化供redis使用
 * @author lenovo
 */
public class User implements Serializable{
	private static final long serialVersionUID = -1000037982L;
	private String userid;
	private String username;
	private String realname;
	private String password;
	private String org;
	private String email;
	private String createDate;
	private String isLock;
	private String roleid;
	private String city_scope_short;
	private String menu;
	private String city;
	public User(){
		super();
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getIsLock() {
		return isLock;
	}
	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getCity_scope_short() {
		return city_scope_short;
	}
	public void setCity_scope_short(String city_scope_short) {
		this.city_scope_short = city_scope_short;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
