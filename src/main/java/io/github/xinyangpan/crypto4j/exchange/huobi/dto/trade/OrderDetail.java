package io.github.xinyangpan.crypto4j.exchange.huobi.dto.trade;

import java.util.List;

import lombok.Data;

@Data
public class OrderDetail {
	
	private String orderId;
	private OrderResult orderResult;
	private List<Execution> executions;

}
