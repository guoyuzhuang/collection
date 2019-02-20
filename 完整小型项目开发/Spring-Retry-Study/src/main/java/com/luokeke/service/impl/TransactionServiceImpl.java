package com.luokeke.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luokeke.mapper.TransactionMapper;
import com.luokeke.pojo.Transaction;
import com.luokeke.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionMapper transactionMapper;

	@Override
	public int insertTransaction(Transaction transaction) {
		return transactionMapper.insertTransaction(transaction);
	}

	@Override
	public int notifySuccess(String orderNo, String notifySuccessTime) {
		return transactionMapper.notifySuccess(orderNo, notifySuccessTime);
	}

	@Override
	public int notifyFail(String orderNo, String notifySuccessTime) {
		return transactionMapper.notifyFail(orderNo, notifySuccessTime);
	}
}
