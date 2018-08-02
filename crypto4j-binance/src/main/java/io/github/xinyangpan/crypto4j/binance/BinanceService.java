package io.github.xinyangpan.crypto4j.binance;

import io.github.xinyangpan.crypto4j.binance.rest.BinanceRestService;
import io.github.xinyangpan.crypto4j.binance.websocket.BinanceMarketStreamWsConnector;
import io.github.xinyangpan.crypto4j.binance.websocket.BinanceUserStreamWsConnector;
import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceSubscriber;
import lombok.Data;

@Data
public class BinanceService {
	private final BinanceSubscriber binanceSubscriber;
	private final BinanceProperties binanceProperties;
	private final BinanceRestService binanceRestService;
	private final BinanceUserStreamWsConnector binanceUserStreamWsConnector;
	private final BinanceMarketStreamWsConnector binanceMarketStreamWsConnector;

	public BinanceService(BinanceSubscriber binanceSubscriber, BinanceProperties binanceProperties) {
		this(binanceSubscriber, binanceProperties, true);
	}

	public BinanceService(BinanceSubscriber binanceSubscriber, BinanceProperties binanceProperties, boolean useUserStream) {
		this.binanceSubscriber = binanceSubscriber;
		this.binanceProperties = binanceProperties;
		this.binanceRestService = new BinanceRestService(binanceProperties.getRestProperties());
		this.binanceMarketStreamWsConnector = new BinanceMarketStreamWsConnector(binanceSubscriber, binanceProperties);
		if (useUserStream) {
			this.binanceUserStreamWsConnector = new BinanceUserStreamWsConnector(binanceSubscriber, binanceProperties);
		} else {
			this.binanceUserStreamWsConnector = null;
		}
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
		if (this.binanceUserStreamWsConnector != null) {
			binanceUserStreamWsConnector.connect();
		}
		binanceMarketStreamWsConnector.connect();
	}

	public void stop() {
		if (this.binanceUserStreamWsConnector != null) {
			binanceUserStreamWsConnector.disconnect();
		}
		binanceMarketStreamWsConnector.disconnect();
	}

}
