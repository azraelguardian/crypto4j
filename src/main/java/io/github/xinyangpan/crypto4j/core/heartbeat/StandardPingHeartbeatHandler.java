package io.github.xinyangpan.crypto4j.core.heartbeat;

import java.io.IOException;

import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

public interface StandardPingHeartbeatHandler extends HeartbeatHandler {

	@Override
	default void sendPing(WebSocketSession session) throws IOException {
		session.sendMessage(new PingMessage());
	}

}
