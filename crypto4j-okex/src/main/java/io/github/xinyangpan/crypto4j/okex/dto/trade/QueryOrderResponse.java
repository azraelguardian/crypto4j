package io.github.xinyangpan.crypto4j.okex.dto.trade;

import java.util.List;

import io.github.xinyangpan.crypto4j.okex.dto.common.OkexRestResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class QueryOrderResponse extends OkexRestResponse {

	private List<OrderResult> orders;

}
