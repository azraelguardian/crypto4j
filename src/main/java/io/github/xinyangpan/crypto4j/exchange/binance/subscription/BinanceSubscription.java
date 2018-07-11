package io.github.xinyangpan.crypto4j.exchange.binance.subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.exchange.binance.dto.Ticker;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.common.StreamData;
import io.github.xinyangpan.crypto4j.exchange.binance.dto.depth.Depth;
import lombok.Data;

@Data
public class BinanceSubscription {
	private final List<StreamSubscription> subscriptions = new ArrayList<>();
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
			subscriptions.add(new DepthSubscription(symbol, level));
		}
		return this;
	}

	public BinanceSubscription ticker(String... symbols) {
		Preconditions.checkNotNull(symbols);
		// 
		for (String symbol : symbols) {
			subscriptions.add(new TickerSubscription(symbol));
		}
		return this;
	}

	public String getUrlParameter() {
		List<String> streamNames = subscriptions.stream()//
			.map(StreamSubscription::getStreamName)//
			.collect(Collectors.toList());
		return Joiner.on('/').join(streamNames);
	}

}
