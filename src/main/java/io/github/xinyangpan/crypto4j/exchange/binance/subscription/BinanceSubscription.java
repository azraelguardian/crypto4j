package io.github.xinyangpan.crypto4j.exchange.binance.subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.Ticker;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.common.StreamData;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.depth.Depth;
import lombok.Data;

@Data
public class BinanceSubscription {
	private final List<String> subscriptions = new ArrayList<>();
	private Consumer<StreamData<Depth>> depthListener;
	private Consumer<StreamData<Ticker>> tickerListener;

	public BinanceSubscription depthListener(Consumer<StreamData<Depth>> depthListener) {
		this.depthListener = depthListener;
		return this;
	}

	public BinanceSubscription tickerListener(Consumer<StreamData<Ticker>> tickerListener) {
		this.tickerListener = tickerListener;
		return this;
	}
	
	public BinanceSubscription depthAndTicker(int level, String... symbols) {
		Preconditions.checkNotNull(symbols);
		// 
		this.depth(level, symbols);
		this.ticker(symbols);
		return this;
	}

	public BinanceSubscription depth(int level, String... symbols) {
		Preconditions.checkNotNull(symbols);
		// 
		for (String symbol : symbols) {
			subscriptions.add(String.format("%s@depth%s", symbol, level));
		}
		return this;
	}

	public BinanceSubscription ticker(String... symbols) {
		Preconditions.checkNotNull(symbols);
		// 
		for (String symbol : symbols) {
			subscriptions.add(String.format("%s@ticker", symbol));
		}
		return this;
	}

	public String getUrlParameter() {
		return Joiner.on('/').join(subscriptions);
	}

}
