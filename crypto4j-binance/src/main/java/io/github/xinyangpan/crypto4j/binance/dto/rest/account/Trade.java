package io.github.xinyangpan.crypto4j.binance.dto.rest.account;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Trade {

	private String symbol;
	private Long id;
	private Long orderId;
	private BigDecimal price;
	private BigDecimal qty;
	private BigDecimal commission;
	private String commissionAsset;
	private Long time;
	private Boolean buyer;
	private Boolean maker;
	private Boolean bestMatch;

}
