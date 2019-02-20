package com.luokeke.pojo;

import java.io.Serializable;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String orderNo;
	
	private String status;//0：未支付成功、1：支付成功
	
	private String createTime;
	
	private String paySuccessTime;

	public Order(String orderNo, String createTime) {
		super();
		this.orderNo = orderNo;
		this.createTime = createTime;
	}

	public Order() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPaySuccessTime() {
		return paySuccessTime;
	}

	public void setPaySuccessTime(String paySuccessTime) {
		this.paySuccessTime = paySuccessTime;
	}
}
