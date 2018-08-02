package io.github.xinyangpan.crypto4j.binance.dto.websocket.userstream;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.binance.dto.enums.ExecutionType;
import io.github.xinyangpan.crypto4j.binance.dto.enums.OrderStatus;
import io.github.xinyangpan.crypto4j.binance.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.binance.dto.enums.Side;
import io.github.xinyangpan.crypto4j.binance.dto.enums.TimeInForce;
import io.github.xinyangpan.crypto4j.binance.dto.websocket.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExecutionReport extends BaseDto {

	@JsonProperty("s")
	private String symbol;
	@JsonProperty("c")
	private String clientOrderId;
	@JsonProperty("S")
	private Side side;
	@JsonProperty("o")
	private OrderType type;
	@JsonProperty("f")
	private TimeInForce timeInForce;
	@JsonProperty("q")
	private BigDecimal quantity;
	@JsonProperty("p")
	private BigDecimal price;
	@JsonProperty("P")
	private BigDecimal stopPrice;
	@JsonProperty("F")
	private BigDecimal icebergQty;
	@JsonProperty("g")
	private Integer ignore1;
	@JsonProperty("C")
	private String originalClientOrderId;
	@JsonProperty("x")
	private ExecutionType executionType;
	@JsonProperty("X")
	private OrderStatus status;
	@JsonProperty("r")
	private String rejectReason;
	@JsonProperty("i")
	private Long orderId;
	@JsonProperty("l")
	private BigDecimal lastExecutedQuantity;
	@JsonProperty("z")
	private BigDecimal cumulativeFilledQuantity;
	@JsonProperty("L")
	private BigDecimal lastExecutedPrice;
	@JsonProperty("n")
	private BigDecimal commissionAmount;
	@JsonProperty("N")
	private String commissionAsset;
	@JsonProperty("T")
	private Long TransactionTime;
	@JsonProperty("t")
	private Long TradeId;
	@JsonProperty("I")
	private Integer ignore2;
	@JsonProperty("w")
	private Boolean working;
	@JsonProperty("m")
	private Boolean makerSide;
	@JsonProperty("M")
	private Boolean ignore3;
	@JsonProperty("O")
	private Long orderTime;
	@JsonProperty("Z")
	private BigDecimal z;

}
