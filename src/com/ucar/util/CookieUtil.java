package com.ucar.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.ucar.model.bean.LBSUser;


public class CookieUtil {
	/**cookie设置
	 * @param request
	 * @param response
	 * @param user
	 */
	public static void addCookie(LBSUser user,int seconds){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String host = request.getServerName();
        Cookie cookie = null;
		cookie = new Cookie("JSESSIONUID",user.getUsername());//保存用户名到Cookie
		cookie.setPath("/");
		cookie.setDomain(host);
		cookie.setMaxAge(seconds);//设置2小时有效期
		response.addCookie(cookie);
		cookie = new Cookie("JSESSIONAUTH", Encrypt.encryptBASE64(user.getPassword().getBytes()));
		cookie.setPath("/");
		cookie.setDomain(host);
		cookie.setMaxAge(seconds);//设置30分钟有效期
		response.addCookie(cookie); 
        
	}
	
	/**获取cookies
	 * @return
	 */
	public static Cookie[]  getCookies(){
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		return cookies;
	}
  
	/**
	 * 清空cookie
	 */
	public static void deleteCookie() {
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		HttpServletResponse response = ServletActionContext.getResponse();
		if(cookies != null){
			for (Cookie cookie : cookies) {
				cookie = new Cookie(cookie.getName(), null);
				cookie.setMaxAge(0); 
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
	}
	

}
