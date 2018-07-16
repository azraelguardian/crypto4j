package io.github.xinyangpan.crypto4j.exchange.binance.websocket.dto.userstream;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Balance {

	@JsonProperty("a")
	private String asset;
	@JsonProperty("f")
	private String freeAmount;
	@JsonProperty("l")
	private String lockedAmount;

}