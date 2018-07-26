package io.github.xinyangpan.crypto4j.exchange.huobi;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("Huobi")
public class HuobiProperties {

	private String websocketUserStreamBaseUrl;
	private String websocketMarketBaseUrl;

}
