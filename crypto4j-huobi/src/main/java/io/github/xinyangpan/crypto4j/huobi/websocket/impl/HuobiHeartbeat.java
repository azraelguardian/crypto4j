package io.github.xinyangpan.crypto4j.huobi.websocket.impl;

import java.io.IOException;

import org.springframework.web.socket.TextMessage;

import io.github.xinyangpan.crypto4j.core.websocket.Heartbeat;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuobiHeartbeat extends Heartbeat {

	public HuobiHeartbeat() {
		this.setTimeout(10);
	}

	@Override
	protected void sendPing() throws IOException {
		String pingMsg = String.format("{\"ping\": %s}", System.currentTimeMillis());
		if (subscriber != null) {
			subscriber.sendMessage(new TextMessage(pingMsg), true);
		} else {
			log.info("subscriber is null, will not send heartbeat this time.");
		}
	}

}
