package io.github.xinyangpan.crypto4j.exchange.binance;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.github.xinyangpan.crypto4j.common.RestProperties;
import lombok.Data;

@Data
@ConfigurationProperties("binance")
public class BinanceProperties {

	private String websocketUserStreamBaseUrl;
	private String websocketMarketBaseUrl;
	private RestProperties restProperties;
	
}
