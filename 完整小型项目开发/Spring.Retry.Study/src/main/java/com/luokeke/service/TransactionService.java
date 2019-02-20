package com.luokeke.service;

import com.luokeke.pojo.Transaction;

public interface TransactionService {

	public int insertTransaction(Transaction transaction);

	public int notifySuccess(String orderNo, String notifySuccessTime);

	public int notifyFail(String orderNo, String notifySuccessTime);

}
