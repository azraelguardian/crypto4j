package io.github.xinyangpan.crypto4j.exchange.huobi.dto.websocket.depth;

import io.github.xinyangpan.crypto4j.exchange.huobi.dto.HuobiHasSymbol;
import lombok.Data;

@Data
public class DepthData implements HuobiHasSymbol {

	private String ch;
	private Long ts;
	private Depth tick;

}