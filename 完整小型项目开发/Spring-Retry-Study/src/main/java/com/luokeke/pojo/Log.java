package com.luokeke.pojo;

import java.io.Serializable;

public class Log implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer lid;
	
	private String orderNo;
	
	private String updateTime;
	
	private String message;

	public Log(String orderNo, String updateTime, String message) {
		super();
		this.orderNo = orderNo;
		this.updateTime = updateTime;
		this.message = message;
	}

	public Log() {
		super();
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
