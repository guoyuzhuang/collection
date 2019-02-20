package com.luokeke.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luokeke.mapper.LogMapper;
import com.luokeke.mapper.TransactionMapper;
import com.luokeke.pojo.Log;
import com.luokeke.service.TransactionService;
import com.luokeke.util.DateUtil;
import com.luokeke.util.SendRestfor;

/**
 * 模拟第一次立即通知支付成功
 * @author guoyuzhuang
 *
 */
@Component
public class FirstSendThread {

	private static Logger logger = LoggerFactory.getLogger(FirstSendThread.class);

	private static ExecutorService exectutorsList = Executors.newCachedThreadPool();

	@Autowired
	private LogMapper logMapper;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private RetrySendThread retrySendThread;

	public void sendFirst(String orderNo) {
		FirstSend firstSend = new FirstSend(orderNo, logMapper, transactionService, retrySendThread);
		logger.info("开始第一次给订单{}发送异步通知");
		exectutorsList.execute(firstSend);
	}
}

class FirstSend implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(FirstSend.class);
	
	private String orderNo;

	private LogMapper logMapper;

	private TransactionService transactionService;
	
	private RetrySendThread retrySendThread;

	public FirstSend(String orderNo, LogMapper logMapper, TransactionService transactionService, RetrySendThread retrySendThread) {
		super();
		this.orderNo = orderNo;
		this.logMapper = logMapper;
		this.transactionService = transactionService;
		this.retrySendThread = retrySendThread;
	}

	@Override
	public void run() {
		if (SendRestfor.sendGet(orderNo)) {
			Log log = new Log(orderNo, DateUtil.getTime(), "异步通知成功");
			logMapper.insertLog(log);
			logger.info("订单支付成功，需要修改流水状态");
			transactionService.notifySuccess(orderNo, DateUtil.getTime());
		} else {
			logger.info("订单支付失败，需要加入到ReTry队列中");
			Log log = new Log(orderNo, DateUtil.getTime(), "异步通知失败");
			logMapper.insertLog(log);
			transactionService.notifyFail(orderNo, DateUtil.getTime());
			retrySendThread.retrySend(orderNo);

		}
	}
	
}
