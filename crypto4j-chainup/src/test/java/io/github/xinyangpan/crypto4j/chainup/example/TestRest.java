package io.github.xinyangpan.crypto4j.chainup.example;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestRest {

	protected static HttpComponentsClientHttpRequestFactory createRequestFactory() {
		CloseableHttpClient httpClient = HttpClients.custom()//
			.setMaxConnTotal(1)//
			.setMaxConnPerRoute(1)//
			.build();
		// 
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setReadTimeout(20 * 1000);
		return requestFactory;
	}

	public static void main(String[] args) {
		// http://localhost:8080/queryRest?q=123
		for (int i = 0; i < 4; i++) {
			new Thread(() -> {
				RestTemplate restTemplate = new RestTemplate(createRequestFactory());
				String string = restTemplate.getForObject("http://localhost:8080/queryRest?q=123", String.class);
				log.info(string);
			}).start();
		}
	}

}
