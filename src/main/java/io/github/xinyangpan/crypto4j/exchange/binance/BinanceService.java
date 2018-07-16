package io.github.xinyangpan.crypto4j.exchange.binance;

import io.github.xinyangpan.crypto4j.exchange.binance.rest.BinanceRestService;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.BinanceMarketStreamWsConnector;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.BinanceUserStreamWsConnector;
import io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl.BinanceSubscriber;
import lombok.Data;

@Data
public class BinanceService {
	private final BinanceSubscriber binanceSubscriber;
	private final BinanceProperties binanceProperties;
	private final BinanceRestService binanceRestService;
	private final BinanceUserStreamWsConnector binanceUserStreamWsConnector;
	private final BinanceMarketStreamWsConnector binanceMarketStreamWsConnector;

	public BinanceService(BinanceSubscriber binanceSubscriber, BinanceProperties binanceProperties) {
		this.binanceSubscriber = binanceSubscriber;
		this.binanceProperties = binanceProperties;
		this.binanceRestService = new BinanceRestService(binanceProperties);
		this.binanceUserStreamWsConnector = new BinanceUserStreamWsConnector(binanceSubscriber, binanceProperties);
		this.binanceMarketStreamWsConnector = new BinanceMarketStreamWsConnector(binanceSubscriber, binanceProperties);
	}

	public BinanceRestService restService() {
		return binanceRestService;
	}

	public BinanceUserStreamWsConnector userStream() {
		return binanceUserStreamWsConnector;
	}

	public BinanceMarketStreamWsConnector marketStream() {
		return binanceMarketStreamWsConnector;
	}

	public void start() {
		binanceUserStreamWsConnector.connect();
		binanceMarketStreamWsConnector.connect();
	}

	public void stop() {
		binanceUserStreamWsConnector.disconnect();
		binanceMarketStreamWsConnector.disconnect();
	}

}
