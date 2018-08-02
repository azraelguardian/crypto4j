package io.github.xinyangpan.crypto4j.okex;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("okex")
public class OkexProperties {

	private String websocketUserStreamBaseUrl;
	private String websocketMarketBaseUrl;

}
