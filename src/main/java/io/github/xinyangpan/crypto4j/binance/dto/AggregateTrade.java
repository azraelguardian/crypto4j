
package io.github.xinyangpan.crypto4j.binance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AggregateTrade extends BaseTrade {

	@JsonProperty("a")
	private long aggregateTradeId;
	@JsonProperty("f")
	private long firstTradeId;
	@JsonProperty("l")
	private long lastTradeId;

	@Override
	public String toString() {
		return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", this.getTradeTimeText(), price, quantity, eventType, symbol, this.getEventTimeText(), aggregateTradeId, firstTradeId, lastTradeId, buyerMaker);
	}

	public long getAggregateTradeId() {
		return aggregateTradeId;
	}

	public void setAggregateTradeId(long aggregateTradeId) {
		this.aggregateTradeId = aggregateTradeId;
	}

	public long getFirstTradeId() {
		return firstTradeId;
	}

	public void setFirstTradeId(long firstTradeId) {
		this.firstTradeId = firstTradeId;
	}

	public long getLastTradeId() {
		return lastTradeId;
	}

	public void setLastTradeId(long lastTradeId) {
		this.lastTradeId = lastTradeId;
	}

}