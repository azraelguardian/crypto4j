package io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SymbolRequest extends BaseRequest {

	private String symbol;

}
