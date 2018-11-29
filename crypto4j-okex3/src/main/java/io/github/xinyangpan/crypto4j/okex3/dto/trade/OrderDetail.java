package io.github.xinyangpan.crypto4j.okex3.dto.trade;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

	private Order order;
	private List<Execution> executions;

}
