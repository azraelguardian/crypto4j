package io.github.xinyangpan.crypto4j.core.dto.api;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderCheckResult {
	private String createTime;
	private String symbol;
	private String orderId;
	private BigDecimal price;
	private BigDecimal volume;
	private String clientOrderId;
}
