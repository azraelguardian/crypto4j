package io.github.xinyangpan.crypto4j.exchange.binance.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.common.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.common.websocket.heartbeat.WsHeartbeat;

public class BinanceWsHeartBeat extends WsHeartbeat {

	public BinanceWsHeartBeat(FailureHandler failureHandler) {
		super(failureHandler);
		this.setTimeout(10);
	}

	@Override
	protected void sendPing(WebSocketSession session) throws IOException {
		// no op since binance does not support heatbeat message
	}

}
