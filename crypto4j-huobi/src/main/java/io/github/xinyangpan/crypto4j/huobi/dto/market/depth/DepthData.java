package io.github.xinyangpan.crypto4j.huobi.dto.market.depth;

import io.github.xinyangpan.crypto4j.huobi.dto.HuobiHasSymbol;
import lombok.Data;

@Data
public class DepthData implements HuobiHasSymbol {

	private String ch;
	private Long ts;
	private Depth tick;

}