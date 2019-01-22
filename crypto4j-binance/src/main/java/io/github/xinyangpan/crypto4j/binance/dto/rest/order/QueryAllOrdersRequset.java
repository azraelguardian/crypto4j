package io.github.xinyangpan.crypto4j.binance.dto.rest.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.github.xinyangpan.crypto4j.binance.dto.rest.common.TimeRangeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class QueryAllOrdersRequset  extends TimeRangeRequest {

	private String orderId;
}
