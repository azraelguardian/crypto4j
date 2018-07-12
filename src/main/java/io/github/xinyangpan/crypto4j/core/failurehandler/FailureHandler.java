package io.github.xinyangpan.crypto4j.core.failurehandler;

import org.springframework.web.socket.CloseStatus;

public interface FailureHandler {
	
	void pingTimeout();
	
	void onAbnormalConnectionClosed(CloseStatus status);
	
}
