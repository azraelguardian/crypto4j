package io.github.xinyangpan.crypto4j.chainup.dto.data;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.chainup.dto.enums.Side;
import lombok.Data;

@Data
public class TradeResult {

	private Long id;

	@JsonProperty("bid_id")
	private Long bidId;
	@JsonProperty("ask_id")
	private Long askId;
	@JsonProperty("ask_user_id")
	private Long askUserId;
	@JsonProperty("bid_user_id")
	private Long bidUserId;

	@JsonProperty("side")
	private Side side;
	@JsonProperty("fee")
	private BigDecimal fee;
	@JsonProperty("deal_price")
	private BigDecimal dealPrice;
	@JsonProperty("volume")
	private BigDecimal volume;
	@JsonProperty("price")
	private BigDecimal price;

	@JsonProperty("type")
	private String type;
	@JsonProperty("feeCoin")
	private String feeCoin;
	@JsonProperty("ctime")
	private Long ctime;

}
