package io.github.xinyangpan.crypto4j.binance.dto.rest.order;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Fill {

	private BigDecimal price;
	private BigDecimal qty;
	private BigDecimal commission;
	private String commissionAsset;

}
