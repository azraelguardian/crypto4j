package io.github.xinyangpan.crypto4j.binance;

import io.github.xinyangpan.crypto4j.binance.rest.BinanceRestService;
import io.github.xinyangpan.crypto4j.binance.websocket.BinanceMarketManager;
import io.github.xinyangpan.crypto4j.binance.websocket.BinanceUserManager;
import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceSubscriber;
import lombok.Data;

@Data
public class BinanceService {
	private final BinanceSubscriber binanceSubscriber;
	private final BinanceProperties binanceProperties;
	private final BinanceRestService binanceRestService;
	private final BinanceUserManager binanceUserManager;
	private final BinanceMarketManager binanceMarketManager;

	public BinanceService(BinanceSubscriber binanceSubscriber, BinanceProperties binanceProperties) {
		this(binanceSubscriber, binanceProperties, true);
	}

	public BinanceService(BinanceSubscriber binanceSubscriber, BinanceProperties binanceProperties, boolean useUserStream) {
		this.binanceSubscriber = binanceSubscriber;
		this.binanceProperties = binanceProperties;
		this.binanceRestService = new BinanceRestService(binanceProperties.getRestProperties());
		this.binanceMarketManager = new BinanceMarketManager();
		this.binanceMarketManager.setUrl(binanceSubscriber.getUrl(binanceProperties.getWebsocketMarketBaseUrl()));
		this.binanceMarketManager.setSubscriber(binanceSubscriber);
		if (useUserStream) {
			this.binanceUserManager = new BinanceUserManager(binanceProperties);
			this.binanceUserManager.setSubscriber(binanceSubscriber);
		} else {
			this.binanceUserManager = null;
		}
	}

	public BinanceRestService restService() {
		return binanceRestService;
	}

	public BinanceUserManager userStream() {
		return binanceUserManager;
	}

	public BinanceMarketManager marketStream() {
		return binanceMarketManager;
	}

	public void start() {
		if (this.binanceUserManager != null) {
			binanceUserManager.connect();
		}
		binanceMarketManager.connect();
	}

	public void stop() {
		if (this.binanceUserManager != null) {
			binanceUserManager.disconnect();
		}
		binanceMarketManager.disconnect();
	}

}
