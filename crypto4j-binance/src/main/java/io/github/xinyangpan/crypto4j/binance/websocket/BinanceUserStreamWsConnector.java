package io.github.xinyangpan.crypto4j.binance.websocket;

import io.github.xinyangpan.crypto4j.binance.BinanceProperties;
import io.github.xinyangpan.crypto4j.binance.dto.rest.common.ListenKey;
import io.github.xinyangpan.crypto4j.binance.rest.BinanceUserStreamService;
import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceSubscriber;
import io.github.xinyangpan.crypto4j.binance.websocket.impl.BinanceWsHandler;
import io.github.xinyangpan.crypto4j.core.websocket.BaseWsConnector;

public class BinanceUserStreamWsConnector extends BaseWsConnector<BinanceWsHandler> {
	private final BinanceProperties binanceProperties;
	private final BinanceUserStreamService binanceUserStreamService;
	private Thread keeplive;
	private ListenKey listenKey;

	public BinanceUserStreamWsConnector(BinanceSubscriber binanceSubscriber, BinanceProperties binanceProperties) {
		super(null, new BinanceWsHandler(binanceSubscriber));
		this.binanceProperties = binanceProperties;
		this.binanceUserStreamService = new BinanceUserStreamService(binanceProperties.getRestProperties());
	}

	@Override
	public void connect() {
		listenKey = this.binanceUserStreamService.start();
		this.url = binanceProperties.getWebsocketUserStreamBaseUrl() + listenKey.getListenKey();
		// 
		keeplive = new Thread(this::keeplive);
		keeplive.start();
		super.connect();
		
	}
	
	@Override
	public void disconnect() {
		super.disconnect();
		if (keeplive != null && !keeplive.isInterrupted()) {
			keeplive.interrupt();
			keeplive = null;
			listenKey = null;
		}
	}
	
	private void keeplive() {
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(30 * 60 * 1000);
				binanceUserStreamService.keeplive(listenKey.getListenKey());
			} catch (InterruptedException e) {}
		}
	}

}
