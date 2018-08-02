package io.github.xinyangpan.crypto4j.okex.dto.account;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

@Data
public class Fund {

	private Map<String, BigDecimal> free;
	private Map<String, BigDecimal> freezed;

}
