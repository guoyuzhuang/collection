package com.luokeke.controller.pay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luokeke.mapper.OrderMapper;
import com.luokeke.mapper.TransactionMapper;
import com.luokeke.pojo.Order;
import com.luokeke.pojo.Transaction;
import com.luokeke.service.TransactionService;
import com.luokeke.thread.FirstSendThread;
import com.luokeke.util.DateUtil;

/**
 * 模拟第三方支付方通知支付平台支付成功
 * @author guoyuzhuang
 *
 */
@Controller
public class PaySuccessController {

	private static Logger logger = LoggerFactory.getLogger(PaySuccessController.class);

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private FirstSendThread firstSendThread;

	@Autowired
	private TransactionService transactionService;

	@ResponseBody
	@RequestMapping("/paySuccess")
	public String paySuccess(@RequestParam(required = true) String orderNo) {
		logger.info("支付平台收到支付请求并且返回成功：" + orderNo);
		Order order = new Order(orderNo, DateUtil.getTime());
		int count = orderMapper.insertOrder(order);
		Transaction transaction = new Transaction(orderNo, DateUtil.getTime());
		int count1 = transactionService.insertTransaction(transaction);
		if (1 == count && count1 == 1) {
			firstSendThread.sendFirst(orderNo);
			return "success";
		}
		return "error";
	}
}
