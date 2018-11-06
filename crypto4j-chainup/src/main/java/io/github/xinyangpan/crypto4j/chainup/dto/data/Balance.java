package io.github.xinyangpan.crypto4j.chainup.dto.data;

import lombok.Data;

// {"normal":"360.00","btcValuatin":"0.15432098","locked":"640.00","coin":"usdt"}
@Data
public class Balance {

	private String normal;
	private String btcValuatin;
	private String locked;
	private String coin;

}
