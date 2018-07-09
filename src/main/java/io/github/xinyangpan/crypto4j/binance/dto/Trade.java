package io.github.xinyangpan.crypto4j.binance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Trade extends BaseTrade {

	@JsonProperty("t")
	private long tradeId;
	@JsonProperty("b")
	private long buyerOrderId;
	@JsonProperty("a")
	private long sellerOrderId;

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", this.getTradeTimeText(), price, quantity, eventType, this.getEventTimeText(), symbol, tradeId, buyerOrderId, sellerOrderId, buyerMaker);
	}

	public long getTradeId() {
		return tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}

	public long getBuyerOrderId() {
		return buyerOrderId;
	}

	public void setBuyerOrderId(long buyerOrderId) {
		this.buyerOrderId = buyerOrderId;
	}

	public long getSellerOrderId() {
		return sellerOrderId;
	}

	public void setSellerOrderId(long sellerOrderId) {
		this.sellerOrderId = sellerOrderId;
	}

	public long getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(long tradeTime) {
		this.tradeTime = tradeTime;
	}

}