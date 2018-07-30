package io.github.xinyangpan.crypto4j.exchange.okex.dto.trade;

import java.util.List;

import io.github.xinyangpan.crypto4j.exchange.okex.dto.common.RestResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class QueryOrderResponse extends RestResponse {

	private List<OrderResult> orders;

}
