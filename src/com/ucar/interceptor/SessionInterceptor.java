package com.ucar.interceptor;

import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.ucar.actions.UserAction;
import com.ucar.model.User;
import com.ucar.model.bean.LBSUser;
import com.ucar.model.service.LBSUserService;
import com.ucar.util.CookieUtil;
import com.ucar.util.RedisUtil;
import com.ucar.util.SessionUtil;

public class SessionInterceptor extends MethodFilterInterceptor {
	@Autowired
	private UserAction userAction;
	
	private static final long serialVersionUID = -2439918986018222686L;

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		/**
		 * 获取参数
		 * Map<String,Object> params=arg0.getInvocationContext().getParameters();
		 */
		Set<String> exclude=getExcludeMethodsSet();
		/**
		 *  获取方法名称
		 */
		String actionName=arg0.getInvocationContext().getName();
		ActionContext actionContext = arg0.getInvocationContext();   
		HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);   
		HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		String type = request.getHeader("X-Requested-With");    
		boolean interceptFlag=true;
		for(String method:exclude){
			if(method.equals(actionName)){
				interceptFlag=false;
				break;
			}
		}
		if(interceptFlag){
			if(SessionUtil.getUserInfo()!=null){
				return arg0.invoke();
			}else{
				Cookie[] cookies = CookieUtil.getCookies();
				if(cookies != null){
					String usernameCookie = null;
					String passwordCookie = null;  
					for (Cookie cookie : cookies) {  
						if ("JSESSIONUID".equals(cookie.getName())) { 
							usernameCookie = cookie.getValue();
						}
						if ("JSESSIONAUTH".equals(cookie.getName())) { 
							passwordCookie = cookie.getValue();
						}
					}
					if (usernameCookie != null && passwordCookie != null) { // 如果存在  
						if(userAction.cookieLogin(usernameCookie ,passwordCookie)){
							return arg0.invoke();
						}
					}
				}
				//从redis中读取
				String requestIp = userAction.getIpAddr(request);
				String username = (String) RedisUtil.getDataFromRedis(LBSUserService.AUTH_REQUEST_IP+requestIp,String.class);
				if(username != null){
					LBSUser user = (LBSUser) RedisUtil.getDataFromRedis(LBSUserService.AUTH_USER_PREFIX+username,LBSUser.class);
					if(user != null){
						return arg0.invoke();
					}
				}
				if ("XMLHttpRequest".equalsIgnoreCase(type)) {// ajax请求
					PrintWriter printWriter = response.getWriter();  
	                printWriter.print("timeout");  
	                printWriter.flush();  
	                printWriter.close();  
					return null;
				}else{
					return SessionUtil.SESSION_INVALID;
				}
			}
			
		}
		return arg0.invoke();		
	}

}
