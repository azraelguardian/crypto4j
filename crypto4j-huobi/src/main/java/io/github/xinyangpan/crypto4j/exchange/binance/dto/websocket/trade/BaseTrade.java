package io.github.xinyangpan.crypto4j.exchange.binance.dto.websocket.trade;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.websocket.common.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BaseTrade extends BaseDto {

	@JsonProperty("s")
	private String symbol;
	@JsonProperty("p")
	protected BigDecimal price;
	@JsonProperty("q")
	protected BigDecimal quantity;
	@JsonProperty("M")
	protected boolean ignore;
	@JsonProperty("m")
	protected boolean buyerMaker;
	@JsonProperty("T")
	protected long tradeTime;

	public String getTradeTimeText() {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(tradeTime), ZoneId.systemDefault());
		return DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
	}


}
