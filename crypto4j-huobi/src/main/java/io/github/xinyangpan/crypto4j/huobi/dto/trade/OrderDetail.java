package io.github.xinyangpan.crypto4j.huobi.dto.trade;

import java.math.BigDecimal;
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
		if (orderState == OrderState.SUBMITTING || orderState == OrderState.SUBMITTED) {
			return false;
		}
		BigDecimal orderFilledAmount = orderResult.getFieldAmount() != null ? orderResult.getFieldAmount() : BigDecimal.ZERO;
		BigDecimal execFilledAmount = getExecutionsFilledAmount();
		if (orderFilledAmount.compareTo(execFilledAmount) != 0) {
			return false;
		} else {
			return true;
		}
	}

	private BigDecimal getExecutionsFilledAmount() {
		BigDecimal execFilledAmount = BigDecimal.ZERO;
		if (executions != null) {
			for (Execution execution : executions) {
				execFilledAmount = execFilledAmount.add(execution.getFilledAmount());
			}
		}
		return execFilledAmount;
	}

}
