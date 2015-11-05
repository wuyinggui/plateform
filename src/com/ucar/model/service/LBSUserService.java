package com.ucar.model.service;

import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

import com.ucar.model.bean.LBSUser;


public interface LBSUserService {
	public final static String TOKEN="4a4c534fa374a459e8717c2843af5043";
	public final static String AUTH_USER_PREFIX = "AUTH_PLATFORM_";
	public final static String AUTH_REQUEST_IP = "AUTH_IP_";
	public final static String SYSFLG="2";
	public final static String AUTH_TYPE_OPERATION_STATUS="1";
	public final static String CRM_LOGIN_URL="http://crmbeta.aayongche.com/lbs/login";
	JSONObject LBSLoginValidate(LBSUser entity);

}
