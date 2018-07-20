package io.github.xinyangpan.crypto4j.exchange.huobi.dto.ws.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HuobiWsRequest {

	private String id;
	private String sub;

}