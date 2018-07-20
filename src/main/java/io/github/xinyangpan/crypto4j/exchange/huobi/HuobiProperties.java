package io.github.xinyangpan.crypto4j.exchange.huobi;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("Huobi")
public class HuobiProperties {

	private String restKey;
	private String restSecret;
	private String restBaseUrl;
	private String websocketUserStreamBaseUrl;
	private String websocketMarketBaseUrl;

}
