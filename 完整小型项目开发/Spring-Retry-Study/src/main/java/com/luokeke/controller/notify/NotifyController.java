package com.luokeke.controller.notify;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luokeke.mapper.OrderMapper;
import com.luokeke.util.DateUtil;

/**
 * 模拟电商平台接受支付结果
 * @author guoyuzhuang
 *
 */
@Controller
public class NotifyController {
	
	private static Logger logger = LoggerFactory.getLogger(NotifyController.class);

	@Autowired
	private OrderMapper orderMapper;

	@ResponseBody
	@RequestMapping("/notify_url")
	public String notify_url(String orderNo) {
		logger.info("电商平台收到支付平台请求，orderNo：" + orderNo);
		if (new Random().nextInt(10) > 7) {
			logger.info("电商平台处理异步通知请求成功");
			int countpay = orderMapper.paySuccess(orderNo, DateUtil.getTime());
			return "success";
		} else {
			logger.info("电商平台处理异步通知请求失败，需要重新通知");
			return "false";
		}
	}
}
