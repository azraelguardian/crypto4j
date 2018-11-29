package io.github.xinyangpan.crypto4j.okex3.dto.account;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BalanceInfo {

	private Long id;
	private BigDecimal frozen;
	private BigDecimal hold;
	private String currency;
	private BigDecimal balance;
	private BigDecimal available;
	private BigDecimal holds;

}
