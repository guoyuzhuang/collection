package com.luokeke.Spring.Retry.Study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.luokeke.ReTryApplication;
import com.luokeke.mapper.LogMapper;
import com.luokeke.mapper.OrderMapper;
import com.luokeke.mapper.TransactionMapper;
import com.luokeke.pojo.Log;
import com.luokeke.pojo.Order;
import com.luokeke.pojo.Transaction;
import com.luokeke.service.TransactionService;
import com.luokeke.util.DateUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReTryApplication.class)
public class ApplicationTests {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private LogMapper logMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Test
	public void insertTransaction() {
		Transaction transaction = new Transaction("10000wrwer", DateUtil.getTime());
		int count = transactionService.insertTransaction(transaction);
		System.out.println("执行结果：---------------------\t" + count);
	}

	@Test
	public void insertLog() {
		Log log = new Log("10000wrwer", DateUtil.getTime(), "异步通知成功");
		int count = logMapper.insertLog(log);
		System.out.println("执行结果：---------------------\t" + count);
	}

	@Test
	public void insertOrder() {
		String orderNo = "luokeke";
		Order order = new Order(orderNo, DateUtil.getTime());
		int count = orderMapper.insertOrder(order);
		System.out.println("执行结果：---------------------\t" + count);
		int countpay = orderMapper.paySuccess(orderNo, DateUtil.getTime());
	}

	@Test
	public void TransactionNotify() {
		String orderNo = "100";
//		int count = transactionMapper.notifyFail(orderNo, DateUtil.getTime());
		int count = transactionService.notifySuccess(orderNo, DateUtil.getTime());
	}

}
