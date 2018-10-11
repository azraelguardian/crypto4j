package io.github.xinyangpan.crypto4j.huobi.dto.account;

import java.util.List;

import io.github.xinyangpan.crypto4j.huobi.dto.enums.AccountState;
import io.github.xinyangpan.crypto4j.huobi.dto.enums.AccountType;
import lombok.Data;

@Data
public class BalanceInfo {

	private Long id;
	private AccountType type;
	private AccountState state;
	private List<Balance> list;

}
