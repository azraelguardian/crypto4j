package io.github.xinyangpan.crypto4j.exchange.huobi.dto.account;

import lombok.Data;

@Data
public class AccountInfo {
	
	private Long id;
	private String type;
	private String subtype;
	private String state;

}
