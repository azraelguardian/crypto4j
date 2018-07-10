package io.github.xinyangpan.crypto4j.core;

import org.springframework.util.Assert;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import io.github.xinyangpan.crypto4j.core.heartbeat.Heartbeat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseWsHandler extends AbstractWebSocketHandler {
	protected String name;
	protected @Getter WebSocketSession session;
	protected @Getter @Setter Heartbeat heartbeat;

	public BaseWsHandler() {
	}

	public BaseWsHandler(String name) {
		this.name = name;
	}

	@Override
	public final synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("Connection Established[{}].", name);
		if (heartbeat != null) {
			heartbeat.start(session);
		}
		this.session = session;
		this.notifyAll();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("Connection Closed[{}], CloseStatus={}.", name, status);
		if (heartbeat != null) {
			heartbeat.stop();
		}
		this.session = null;
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("Transport Error[{}].", name, exception);
	}

	public final synchronized void waitUtilConnectionEstablished() {
		if (session != null) {
			return;
		}
		try {
			wait();
			Assert.notNull(session, "Session should not be null.");
		} catch (InterruptedException e) {
			Assert.notNull(session, "Interrupted whiling waiting and session is still null.");
		}
	}

}
