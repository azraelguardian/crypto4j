package io.github.xinyangpan.crypto4j.huobi.dto.market.kline;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KlineParam {
	
	private String symbol;
	private String period;
	private Integer size;

}
