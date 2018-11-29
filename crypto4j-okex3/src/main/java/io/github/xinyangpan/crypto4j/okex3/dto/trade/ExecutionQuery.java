package io.github.xinyangpan.crypto4j.okex3.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.okex3.dto.Pageable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ExecutionQuery extends Pageable {

	@JsonProperty("order_id")
	private Long orderId;
	@JsonProperty("instrument_id")
	private String instrumentId;

}
