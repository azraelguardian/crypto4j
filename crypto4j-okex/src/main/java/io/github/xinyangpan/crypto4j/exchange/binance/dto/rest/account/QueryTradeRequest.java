package io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.account;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.common.SymbolRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryTradeRequest extends SymbolRequest {
	
	private Long startTime;
	private Long endTime;
	private Long fromId;
	private Integer limit;
	
}
