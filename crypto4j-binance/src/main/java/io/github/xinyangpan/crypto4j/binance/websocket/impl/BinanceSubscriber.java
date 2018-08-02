package io.github.xinyangpan.crypto4j.binance.websocket.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.binance.dto.depth.Depth;
import io.github.xinyangpan.crypto4j.binance.dto.websocket.Ticker;
import io.github.xinyangpan.crypto4j.binance.dto.websocket.common.StreamData;
import io.github.xinyangpan.crypto4j.binance.dto.websocket.userstream.AccountInfo;
import io.github.xinyangpan.crypto4j.binance.dto.websocket.userstream.ExecutionReport;
import io.github.xinyangpan.crypto4j.core.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.core.websocket.Subscriber;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinanceSubscriber extends Subscriber {
	private final List<String> streamNames = new ArrayList<>();
	private Consumer<StreamData<Depth>> depthListener = Crypto4jUtils.logConsumer();
	private Consumer<StreamData<Ticker>> tickerListener = Crypto4jUtils.logConsumer();
	private Consumer<AccountInfo> accountInfoListener = Crypto4jUtils.logConsumer();
	private Consumer<ExecutionReport> executionReportListener = Crypto4jUtils.logConsumer();

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
	
	public String getUrl(String websocketMarketBaseUrl) {
		return websocketMarketBaseUrl + this.getUrlParameter();
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

	public void onAccountInfo(AccountInfo data) {
		accountInfoListener.accept(data);
	}

	public void onExecutionReport(ExecutionReport data) {
		executionReportListener.accept(data);
	}

}
