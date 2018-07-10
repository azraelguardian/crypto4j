package io.github.xinyangpan.crypto4j.core.heartbeat;

import org.springframework.web.socket.WebSocketSession;

public interface HeartBeatHandler {

	void sendPing(WebSocketSession session);

	void pingTimeout();

}
