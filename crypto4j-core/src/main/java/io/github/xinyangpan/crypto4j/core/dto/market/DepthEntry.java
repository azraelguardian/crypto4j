package io.github.xinyangpan.crypto4j.core.dto.market;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DepthEntry {

	private BigDecimal price;
	private BigDecimal quantity;

}
