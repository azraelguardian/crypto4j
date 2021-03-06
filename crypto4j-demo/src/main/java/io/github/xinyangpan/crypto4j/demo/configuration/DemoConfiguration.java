package io.github.xinyangpan.crypto4j.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;

@Configuration
public class DemoConfiguration {

	@Bean
	public HuobiRestService huobiRestService() {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://api.huobi.pro");
//		restProperties.setRestKey(Crypto4jUtils.getSecret("huobi.key"));
//		restProperties.setRestSecret(Crypto4jUtils.getSecret("huobi.secret"));
		return new HuobiRestService(restProperties);
	}
	
}
