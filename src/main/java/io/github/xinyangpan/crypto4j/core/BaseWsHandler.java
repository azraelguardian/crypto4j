package io.github.xinyangpan.crypto4j.core;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import io.github.xinyangpan.crypto4j.core.heartbeat.Heartbeat;
import io.github.xinyangpan.crypto4j.core.heartbeat.HeartbeatHandler;
import io.github.xinyangpan.crypto4j.core.heartbeat.StandardPingHeartbeatHandler;
import io.github.xinyangpan.crypto4j.core.subscriber.DynamicWsSubscriber;
import io.github.xinyangpan.crypto4j.core.subscriber.WsSubscriber;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseWsHandler<T extends WsSubscriber> extends AbstractWebSocketHandler {
	protected String name;
	protected @Getter WebSocketSession session;
	protected Heartbeat heartbeat;
	protected T wsSubscriber;

	public BaseWsHandler() {
	}

	public BaseWsHandler(String name, T wsSubscriber) {
		this.name = name;
		if (wsSubscriber instanceof DynamicWsSubscriber) {
			((DynamicWsSubscriber) wsSubscriber).setSessionSupplier(this::getSessionUtilReady);
		}
		this.wsSubscriber = wsSubscriber;
	}

	@Override
	public final synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("Connection Established[{}].", name);
		if (heartbeat != null) {
			heartbeat.start(session);
		}
		this.session = session;
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

	public final WebSocketSession getSessionUtilReady() {
		return this.getSessionUtilReady(3, 1);
	}
	
	public final WebSocketSession getSessionUtilReady(int attempt, long retryInterval) {
		try {
			for (int i = 0; i < attempt; i++) {
				if (session != null && session.isOpen()) {
					return session;
				}
				Thread.sleep(retryInterval * 1000);
			}
			throw new RuntimeException("Timeout.");
		} catch (InterruptedException e) {
			return null;
		}
	}

	public void setHeartbeatHandler(HeartbeatHandler heartbeatHandler) {
		this.heartbeat = new Heartbeat(heartbeatHandler);
	}

	public void setStandardPingHeartbeatHandler(StandardPingHeartbeatHandler heartbeatHandler) {
		this.setHeartbeatHandler(heartbeatHandler);
	}

}
