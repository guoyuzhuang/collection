package com.luokeke.thread;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import com.luokeke.mapper.LogMapper;
import com.luokeke.pojo.Log;
import com.luokeke.retry.CustomExponentialBackOffPolicy;
import com.luokeke.service.TransactionService;
import com.luokeke.util.DateUtil;
import com.luokeke.util.SendRestfor;

/**
 * 模拟第一次立即通知支付成功
 * @author guoyuzhuang
 *
 */
@Component
public class RetrySendThread {

	private static Logger logger = LoggerFactory.getLogger(RetrySendThread.class);

	private static ExecutorService exectutorsList = Executors.newCachedThreadPool();

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private LogMapper logMapper;

	public void retrySend(String orderNo) {
		logger.info("重试机制启动，订单编号Wie：{}", orderNo);
		RetrySend r = new RetrySend(orderNo, logMapper, transactionService);
		exectutorsList.execute(r);
	}
}

class RetrySend implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(RetrySend.class);

	private String orderNo;

	private LogMapper logMapper;

	private TransactionService transactionService;

	public RetrySend(String orderNo, LogMapper logMapper, TransactionService transactionService) {
		this.orderNo = orderNo;
		this.logMapper = logMapper;
		this.transactionService = transactionService;
	}

	@Override
	public void run() {
		RetryTemplate template = new RetryTemplate();
		SimpleRetryPolicy policy = new SimpleRetryPolicy();
		policy.setMaxAttempts(7);
		template.setRetryPolicy(policy);
		CustomExponentialBackOffPolicy backOffPolicy1 = new CustomExponentialBackOffPolicy();
		backOffPolicy1.setMultiplier(Arrays.asList(1, 2, 5, 8));
		template.setBackOffPolicy(backOffPolicy1);
		try {
			// 执行
			String result = template.execute(new RetryCallback<String, Exception>() {

				public String doWithRetry(RetryContext context) throws Exception {
					if (SendRestfor.sendGet(orderNo)) {
						Log log = new Log(orderNo, DateUtil.getTime(), "异步通知成功");
						logMapper.insertLog(log);
						transactionService.notifySuccess(orderNo, DateUtil.getTime());
						return "success";
					}
					Log log = new Log(orderNo, DateUtil.getTime(), "异步通知失败");
					logMapper.insertLog(log);
					transactionService.notifyFail(orderNo, DateUtil.getTime());
					throw new RuntimeException();
				}
			}, new RecoveryCallback<String>() {
				public String recover(RetryContext context) throws Exception {
					logger.info("Retry 策略执行完毕，最终还是没有执行成功");
					return "ReTry failed";
				}
			});
			if ("success".equals(result)) {

			}
		} catch (Exception e) {
			logger.info("重试机制出现异常：" + e.toString());
		}
	}

}
