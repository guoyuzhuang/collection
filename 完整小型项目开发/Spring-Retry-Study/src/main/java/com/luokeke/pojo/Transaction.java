package com.luokeke.pojo;

import java.io.Serializable;

public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer tid;
	
	private String orderNo;
	
	private Integer notifyCount;
	
	private String notifyStatus;
	
	private String notifySuccessTime;

	public Transaction(String orderNo, String notifySuccessTime) {
		super();
		this.orderNo = orderNo;
		this.notifySuccessTime = notifySuccessTime;
	}

	public Transaction() {
		super();
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getNotifyCount() {
		return notifyCount;
	}

	public void setNotifyCount(Integer notifyCount) {
		this.notifyCount = notifyCount;
	}

	public String getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(String notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	public String getNotifySuccessTime() {
		return notifySuccessTime;
	}

	public void setNotifySuccessTime(String notifySuccessTime) {
		this.notifySuccessTime = notifySuccessTime;
	}
}
