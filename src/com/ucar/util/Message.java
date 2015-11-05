package com.ucar.util;

public class Message {
	/**
	 * 操作成功状态
	 */
	private boolean status;
	/**
	 * 提示信息
	 */
	private String msg;
	/**
	 * 异常ID
	 */
	private int exceptionId;
	
	/**
	 * 返回值
	 */
	private Object entity;
	public Message(){
		
	}
	public Message(boolean status){
		this.status=status;
	}
	public Message(boolean status,String msg){
		this.status=status;
		this.msg=msg;
	}
	public Message(boolean status,String msg,int exceptionId){
		this.status=status;
		this.msg=msg;
		this.exceptionId=exceptionId;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getExceptionId() {
		return exceptionId;
	}
	public void setExceptionId(int exceptionId) {
		this.exceptionId = exceptionId;
	}
	public void setEntity(Object entity) {
		this.entity = entity;
	}
	public Object getEntity() {
		return entity;
	}
	
}
