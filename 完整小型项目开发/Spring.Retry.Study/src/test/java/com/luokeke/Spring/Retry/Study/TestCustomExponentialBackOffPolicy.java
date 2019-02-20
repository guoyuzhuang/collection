package com.luokeke.Spring.Retry.Study;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.luokeke.retry.CustomExponentialBackOffPolicy;

/**
 * 测试自定义回避策略
 * @author guoyuzhuang
 *
 */
public class TestCustomExponentialBackOffPolicy {

	@Test
	public void test() throws Exception {
		// Spring-retry提供了RetryOperations接口的实现类RetryTemplate
		RetryTemplate template = new RetryTemplate();

		// 简单重试策略
		SimpleRetryPolicy policy = new SimpleRetryPolicy();
		// 重试5次
		policy.setMaxAttempts(7);
		template.setRetryPolicy(policy);

		// 1. 指数退避策略
		CustomExponentialBackOffPolicy backOffPolicy1 = new CustomExponentialBackOffPolicy();
		backOffPolicy1.setMultiplier(Arrays.asList(1, 2, 5, 8));// 等待倍数 The default 'multiplier' value - value 2 (100% increase per backoff).

		// 设置退避策略
		template.setBackOffPolicy(backOffPolicy1);

		// 执行
		String result = template.execute(new RetryCallback<String, Exception>() {

			public String doWithRetry(RetryContext context) throws Exception {
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				System.out.println("执行时间：" + s.format(new Date()));
				// 抛出异常
				throw new TimeoutException("TimeoutException");
			}
		},
				// 当重试执行完闭，操作还未成为，那么可以通过RecoveryCallback完成一些失败事后处理。
				new RecoveryCallback<String>() {
					public String recover(RetryContext context) throws Exception {
						System.out.println("Retry 策略执行完毕，最终还是没有执行成功");
						return "ReTry failed";
					}
				});

		System.out.println(result);
	}
}
