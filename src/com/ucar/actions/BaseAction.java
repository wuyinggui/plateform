package com.ucar.actions;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.ucar.util.ExceptionType;
import com.ucar.util.Message;

public class BaseAction extends ActionSupport implements RequestAware,SessionAware{
	private static final long serialVersionUID = 36826382682L;
	/**
	 * action2中的session
	 */
	private Map<String, Object> session;
	/**
	 * action2中的request
	 */
	private Map<String, Object> request;
	protected Logger logger = Logger.getLogger(this.getClass().getName());
	public Map<String, Object> getRequest() {
		return request;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	protected Message checkNull(Object o) {
		Message message=new Message(false);
		if(o==null){
			message.setMsg("add information is null");
			message.setExceptionId(ExceptionType.EXC_PLAT_NULL.getValue());
		}
		return message;
	}
}
