package io.github.xinyangpan.crypto4j.binance.dto.rest.account;

import java.util.List;

import lombok.Data;

@Data
public class Account {

	private Integer makerCommission;
	private Integer takerCommission;
	private Integer buyerCommission;
	private Integer sellerCommission;
	private Boolean canTrade;
	private Boolean canWithdraw;
	private Boolean canDeposit;
	private Long updateTime;
	private List<Balance> balances;

}
