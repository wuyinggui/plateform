package com.ucar.util;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.ucar.model.bean.LBSUser;

public class SessionUtil {

	public static final String SESSION_INVALID="session_invalid";
	public static final String USER_KEY="PLATFORM_USER_INFO";
	public static Map<String, Object> getSession(){
		return 	ActionContext.getContext().getSession();	
	}
	public static void setUserInfo(Object o){
		getSession().put(USER_KEY, o);
	}
	public static LBSUser getUserInfo(){
		return (LBSUser)getSession().get(USER_KEY);
	}
	public static void invalidate(){
		getSession().clear();
	}
	public static String getSessionid(){
		return ServletActionContext.getRequest().getSession().getId();
	}
}
