package io.github.xinyangpan.crypto4j.exchange.binance;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("binance")
public class BinanceProperties {

	private String restKey;
	private String restSecret;
	private String restBaseUrl;
	private String websocketUserStreamBaseUrl;
	private String websocketMarketBaseUrl;

}
