package io.github.xinyangpan.crypto4j.exchange.binance.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseTrade extends BaseDto {

	@JsonProperty("s")
	protected String symbol;
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

	@Override
	public String toString() {
		return String.format("BaseTrade [eventType=%s, eventTime=%s, symbol=%s, price=%s, quantity=%s, ignore=%s, buyerMaker=%s]", eventType, eventTime, symbol, price, quantity, ignore, buyerMaker);
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}

	public boolean isBuyerMaker() {
		return buyerMaker;
	}

	public void setBuyerMaker(boolean buyerMaker) {
		this.buyerMaker = buyerMaker;
	}

}
