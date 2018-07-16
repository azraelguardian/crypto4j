package io.github.xinyangpan.crypto4j.exchange.binance.websocket.dto.userstream;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.exchange.binance.websocket.dto.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountInfo extends BaseDto {

	// Maker commission rate (bips)
	@JsonProperty("m")
	private Integer makerCommissionRate;
	// Taker commission rate (bips)
	@JsonProperty("t")
	private Integer takerCommissionRate;
	// Buyer commission rate (bips)
	@JsonProperty("b")
	private Integer buyerCommissionRate;
	// Seller commission rate (bips)
	@JsonProperty("s")
	private Integer sellerCommissionRate;
	@JsonProperty("T")
	private Boolean tradeable;
	@JsonProperty("W")
	private Boolean withdrawable;
	@JsonProperty("D")
	private Boolean depositable;
	@JsonProperty("u")
	private Long updateTime;
	@JsonProperty("B")
	private List<Balance> balances = null;

}