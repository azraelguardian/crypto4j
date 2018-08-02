package io.github.xinyangpan.crypto4j.core.websocket.failurehandler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

public interface FailureHandler {

	void pingTimeout(WebSocketSession session);

	void onAbnormalConnectionClosed(CloseStatus status);

}
