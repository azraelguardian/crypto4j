package io.github.xinyangpan.crypto4j.exchange.binance.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.subscriber.WsSubscriber;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.Ticker;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.common.StreamData;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.depth.Depth;
import lombok.Data;

@Data
public class BinanceSubscriber implements WsSubscriber {
	private final List<String> streamNames = new ArrayList<>();
	private Consumer<StreamData<Depth>> depthListener;
	private Consumer<StreamData<Ticker>> tickerListener;

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

}
