package io.github.xinyangpan.crypto4j.binance.websocket.impl;

import java.io.IOException;

import io.github.xinyangpan.crypto4j.core.websocket.Heartbeat;

public class BinanceHeartBeat extends Heartbeat {

	public BinanceHeartBeat() {
		this.setTimeout(10);
	}

	@Override
	protected void sendPing() throws IOException {
		// no op since binance does not support heatbeat message
	}

}
