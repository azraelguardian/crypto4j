package io.github.xinyangpan.crypto4j.exchange.huobi.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.common.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.common.websocket.heartbeat.WsHeartbeat;

public class HuobiWsHeartBeat extends WsHeartbeat {

	public HuobiWsHeartBeat(FailureHandler failureHandler) {
		super(failureHandler);
		this.setTimeout(10);
	}

	@Override
	protected void sendPing(WebSocketSession session) throws IOException {
		String pingMsg = String.format("{\"ping\": %s}", System.currentTimeMillis());
		session.sendMessage(new TextMessage(pingMsg));
	}

}
