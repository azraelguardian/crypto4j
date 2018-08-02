package io.github.xinyangpan.crypto4j.binance;

import org.springframework.boot.context.properties.ConfigurationProperties;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import lombok.Data;

@Data
@ConfigurationProperties("binance")
public class BinanceProperties {

	private String websocketUserStreamBaseUrl;
	private String websocketMarketBaseUrl;
	private RestProperties restProperties;
	
}
