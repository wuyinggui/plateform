package com.ucar.actions;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;













import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysql.jdbc.StringUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.ucar.model.User;
import com.ucar.model.bean.LBSUser;
import com.ucar.model.service.LBSUserService;
import com.ucar.model.service.UserService;
import com.ucar.model.vo.UserVO;
import com.ucar.util.CookieUtil;
import com.ucar.util.Message;
import com.ucar.util.SessionUtil;
@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction{
	private LBSUser entity; 
	@Autowired
	private LBSUserService lbsUserService;
	@Autowired
	private UserService userService;
	private String result;
	public String login(){
		HttpServletRequest lBSLoginrequest = ServletActionContext.getRequest();
		String requestIp = getIpAddr(lBSLoginrequest);
		Message message=checkNull(entity);
		if(message.getExceptionId()==0){
			if(entity.getUsername()==null || "".equals(entity.getUsername().trim())){
				message.setMsg("用户名为空！");
				addFieldError("username", "用户名不能为空！");
				return LOGIN;
			}
			if(entity.getPassword()==null || "".equals(entity.getPassword().trim())){
				message.setMsg("密码为空！");
				addFieldError("password", "密码不能为空！");
				return LOGIN;
			}
			if(message.getMsg()==null){
				if("1".equals(entity.getIsAuthen())){
						//cookie登录
						boolean flag = cookieLogin(entity.getUsername(), entity.getPassword());
						if(flag){
							//cookie登录成功
							result = "{\"success\":"+true+",\"username\":\""+entity.getUsername()+"\"}";
						}else{
							result = "{\"success\":"+false+"}";
						}
					
					return "json";
				}else{
					//普通登录
					User nEntity=null;
					try {
						nEntity= userService.loginValidate(new User(entity.getUsername(), entity.getPassword()));
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					if(nEntity !=null){
						logger.info("Platform system login sucess: username: "+entity.getUsername()+", password: "+entity.getPassword()+", request from: "+requestIp);
						LBSUser lbsEntity = new LBSUser();
						String timeStamp=Long.toString(System.currentTimeMillis());
						String nonse=Integer.toString((new Random()).nextInt(10000000));
						lbsEntity.setUsername(nEntity.getUsername());
						lbsEntity.setPassword(nEntity.getPassword());
						lbsEntity.setTimeStamp(timeStamp);
						lbsEntity.setNonse(nonse);
						SessionUtil.setUserInfo(lbsEntity);//session存放
						CookieUtil.addCookie(lbsEntity,60*60*2);//cookie设置
						message.setStatus(true);
						message.setMsg(SUCCESS);
						return SUCCESS;
					}else{
						message.setMsg("用户名或密码错误！");
						addFieldError("errorMsg", "用户名或密码错误！");
					}
				}
				
			}
		}
		return LOGIN;
	}
	
	public String logout(){
		SessionUtil.invalidate();
		CookieUtil.deleteCookie();
		return LOGIN;
	}
	
	public String error(){
		return ERROR;
	}
	public String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}

	public LBSUser getEntity() {
		return entity;
	}

	public void setEntity(LBSUser entity) {
		this.entity = entity;
	}

	public synchronized boolean cookieLogin(String usernameCookie,
			String passwordCookie) {
		User nEntity=null;
		try {
			nEntity= userService.cookieLoginValidate(new User(usernameCookie, passwordCookie));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		if(nEntity !=null){
			return true;
		}else{
			return false;
		}
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	} 
	
}
