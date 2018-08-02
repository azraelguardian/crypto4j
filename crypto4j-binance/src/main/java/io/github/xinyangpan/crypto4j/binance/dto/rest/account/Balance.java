package io.github.xinyangpan.crypto4j.binance.dto.rest.account;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Balance {
	
	private String asset;
	private BigDecimal free;
	private BigDecimal locked;
	
}
