package io.github.xinyangpan.crypto4j.core.heartbeat;

import java.io.IOException;

import org.springframework.web.socket.WebSocketSession;

public interface HeartbeatHandler {

	void sendPing(WebSocketSession session) throws IOException;

	void pingTimeout();

}
