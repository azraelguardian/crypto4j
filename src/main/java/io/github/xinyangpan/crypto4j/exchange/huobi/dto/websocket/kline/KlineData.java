package io.github.xinyangpan.crypto4j.exchange.huobi.dto.websocket.kline;

import lombok.Data;

@Data
public class KlineData {

	private String ch;
	private Long ts;
	private Tick tick;

}