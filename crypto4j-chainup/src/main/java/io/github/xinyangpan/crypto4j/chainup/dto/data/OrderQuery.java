package io.github.xinyangpan.crypto4j.chainup.dto.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderQuery {

	@JsonProperty("order_info")
	private OrderInfo orderInfo;
	@JsonProperty("trade_list")
	private List<Trade> tradeList;

}
