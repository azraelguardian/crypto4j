package io.github.xinyangpan.crypto4j.huobi.dto.account;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.huobi.dto.enums.BalanceType;
import lombok.Data;

@Data
public class Balance {

	private String currency;
	private BalanceType type;
	private BigDecimal balance;

}
