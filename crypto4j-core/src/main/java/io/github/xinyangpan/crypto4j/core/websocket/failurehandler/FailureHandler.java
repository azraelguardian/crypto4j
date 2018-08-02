package io.github.xinyangpan.crypto4j.core.websocket.failurehandler;

import org.springframework.web.socket.CloseStatus;

public interface FailureHandler {
	
	void pingTimeout();
	
	void onAbnormalConnectionClosed(CloseStatus status);
	
}
