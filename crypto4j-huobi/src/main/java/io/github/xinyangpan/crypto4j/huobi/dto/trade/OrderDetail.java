package io.github.xinyangpan.crypto4j.huobi.dto.trade;

import java.util.List;

import io.github.xinyangpan.crypto4j.huobi.dto.enums.OrderState;
import lombok.Data;

@Data
public class OrderDetail {

	private String orderId;
	private OrderResult orderResult;
	private List<Execution> executions;

	public boolean isInFinalState() {
		OrderState orderState = orderResult.getState();
		if (orderState == OrderState.SUBMITTING || orderState == OrderState.SUBMITTED || orderState == OrderState.PARTIAL_FILLED) {
			return false;
		}
		
		return true;
	}
}
