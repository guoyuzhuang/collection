package com.luokeke.Spring.Retry.Study;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GenOrderNo {

	public static ExecutorService executorsList = Executors.newFixedThreadPool(10);

	@Test
	public void genOrderNo() {
		GenOrderNo g = new GenOrderNo();
		for (int i = 1000; i < 1100; ++i) {
			String url = "http://luokeke:8080/paySuccess?orderNo=" + i;
			g.sendGet(url);
		}
	}

	/**
	 * 电商平台接受成功，返回true，否则返回false
	 * @param orderNo
	 * @return
	 */
	public void sendGet(String url) {
		/*executorsList.execute(new Runnable() {
			@Override
			public void run() {
				RestTemplate rt = new RestTemplate();
				ParameterizedTypeReference<String> type = new ParameterizedTypeReference<String>() {
				};
				ResponseEntity<String> response = rt.exchange(url.toString(), HttpMethod.GET, null, type);
				String result = response.getBody();
				System.out.println("集中请求返回结果为：" + result);
			}
		});*/
		RestTemplate rt = new RestTemplate();
		ParameterizedTypeReference<String> type = new ParameterizedTypeReference<String>() {
		};
		ResponseEntity<String> response = rt.exchange(url.toString(), HttpMethod.GET, null, type);
		String result = response.getBody();
		System.out.println("集中请求返回结果为：" + result);
	}
}
