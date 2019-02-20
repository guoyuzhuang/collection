package com.luokeke.util;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 模拟发送http请求给电商平台
 * @author guoyuzhuang
 *
 */
public class SendRestfor {

	private static final String SEND_URL = "http://luokeke:8080/notify_url?orderNo=";

	/**
	 * 电商平台接受成功，返回true，否则返回false
	 * @param orderNo
	 * @return
	 */
	public static boolean sendGet(String orderNo) {
		String url = SEND_URL + orderNo;
		RestTemplate rt = new RestTemplate();

		/*
		 * 创建一个响应类型模板。
		 * 就是REST请求的响应体中的数据类型。
		 * ParameterizedTypeReference - 代表REST请求的响应体中的数据类型。
		 */
		ParameterizedTypeReference<String> type = new ParameterizedTypeReference<String>() {
		};

		/*
		 * ResponseEntity:封装了返回值信息，相当于是HTTP Response中的响应体。
		 * 发起REST请求。
		 */
		ResponseEntity<String> response = rt.exchange(url.toString(), HttpMethod.GET, null, type);
		/*
		 * ResponseEntity.getBody() - 就是获取响应体中的java对象或返回数据结果。
		 */
		String result = response.getBody();
		if ("success".equals(result))
			return true;
		return false;
	}
}
