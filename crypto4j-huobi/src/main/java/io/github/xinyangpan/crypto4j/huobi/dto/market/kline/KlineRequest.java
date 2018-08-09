package io.github.xinyangpan.crypto4j.huobi.dto.market.kline;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class KlineRequest extends HuobiWsRequest {
	// 单位：秒
	private Long from;
	// 单位：秒
	private Long to;
	
	public KlineRequest(String symbol, String period) {
		this.req = String.format("market.%s.kline.%s", symbol, period);
		this.id = req;
	}
	
}
