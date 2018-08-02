package io.github.xinyangpan.crypto4j.exchange.huobi.dto.market.kline;

import io.github.xinyangpan.crypto4j.exchange.huobi.dto.HuobiHasSymbol;
import lombok.Data;

@Data
public class KlineData  implements HuobiHasSymbol {

	private String ch;
	private Long ts;
	private Tick tick;

}