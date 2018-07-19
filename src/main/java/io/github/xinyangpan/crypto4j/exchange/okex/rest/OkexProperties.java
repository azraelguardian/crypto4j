package io.github.xinyangpan.crypto4j.exchange.okex.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("okex")
public class OkexProperties {

	private String restKey;
	private String restSecret;
	private String restBaseUrl;
	private String websocketUserStreamBaseUrl;
	private String websocketMarketBaseUrl;

}
