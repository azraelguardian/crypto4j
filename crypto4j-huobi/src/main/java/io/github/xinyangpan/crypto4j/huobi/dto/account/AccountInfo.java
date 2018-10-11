package io.github.xinyangpan.crypto4j.huobi.dto.account;

import io.github.xinyangpan.crypto4j.huobi.dto.enums.AccountState;
import io.github.xinyangpan.crypto4j.huobi.dto.enums.AccountType;
import lombok.Data;

@Data
public class AccountInfo {

	private Long id;
	private AccountType type;
	private String subtype;
	private AccountState state;

	public boolean isSpotAndWorking() {
		return AccountType.spot.equals(this.getType()) && AccountState.working.equals(this.getState());
	}

}
