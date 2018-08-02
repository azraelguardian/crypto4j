package io.github.xinyangpan.crypto4j.binance.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.core.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.core.websocket.heartbeat.WsHeartbeat;

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
