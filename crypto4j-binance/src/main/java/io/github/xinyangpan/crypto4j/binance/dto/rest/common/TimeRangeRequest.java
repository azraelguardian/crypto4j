package io.github.xinyangpan.crypto4j.binance.dto.rest.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TimeRangeRequest extends SymbolRequest{
	private Long startTime;
	private Long endTime;
	private Integer limit = 500;
}
