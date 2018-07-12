package io.github.xinyangpan.crypto4j.exchange.binance.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.subscriber.BaseWsSubscriber;
import io.github.xinyangpan.crypto4j.exchange.ExchangeUtils;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.Ticker;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.common.StreamData;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.depth.Depth;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceSubscriber extends BaseWsSubscriber {
	private final List<String> streamNames = new ArrayList<>();
	private Consumer<StreamData<Depth>> depthListener = ExchangeUtils.noOpConsumer();
	private Consumer<StreamData<Ticker>> tickerListener = ExchangeUtils.noOpConsumer();

	public BinanceSubscriber depthListener(Consumer<StreamData<Depth>> depthListener) {
		this.depthListener = depthListener;
		return this;
	}

	public BinanceSubscriber tickerListener(Consumer<StreamData<Ticker>> tickerListener) {
		this.tickerListener = tickerListener;
		return this;
	}

	public BinanceSubscriber depthAndTicker(int level, String... symbols) {
		Preconditions.checkNotNull(symbols);
		// 
		this.depth(level, symbols);
		this.ticker(symbols);
		return this;
	}

	public BinanceSubscriber depth(int level, String... symbols) {
		Preconditions.checkNotNull(symbols);
		// 
		for (String symbol : symbols) {
			streamNames.add(String.format("%s@depth%s", symbol, level));
		}
		return this;
	}

	public BinanceSubscriber ticker(String... symbols) {
		Preconditions.checkNotNull(symbols);
		// 
		for (String symbol : symbols) {
			streamNames.add(String.format("%s@ticker", symbol));
		}
		return this;
	}

	public String getUrlParameter() {
		return Joiner.on('/').join(streamNames);
	}

	public void onTicker(StreamData<Ticker> data) {
		tickerListener.accept(data);
	}

	public void onDepth(StreamData<Depth> data) {
		depthListener.accept(data);
	}

}
