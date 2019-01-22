package io.github.xinyangpan.crypto4j.huobi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.huobi.dto.enums.OrderQueryDirect;
import io.github.xinyangpan.crypto4j.huobi.dto.enums.OrderType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueryAllOrdersRequest  {
	private String symbol;
	private OrderType[] types;
	
	@JsonProperty("start-date")
	private String startDate;
	@JsonProperty("end-date")
	private String endDate;
	
	@Builder.Default
	private String states = "submitting,submitted,partial-filled,partial-canceled,filled,canceled";
	private String from;
	private OrderQueryDirect direct;
	private Integer size;
}
