package io.github.xinyangpan.crypto4j.common.websocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import io.github.xinyangpan.crypto4j.common.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.common.websocket.heartbeat.WsHeartbeat;
import io.github.xinyangpan.crypto4j.common.websocket.subscriber.DynamicWsSubscriber;
import io.github.xinyangpan.crypto4j.common.websocket.subscriber.WsSubscriber;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseWsHandler<T extends WsSubscriber> extends AbstractWebSocketHandler {
	protected @Setter String name;
	protected @Getter WebSocketSession session;
	protected @Getter @Setter WsHeartbeat wsHeartbeat;
	protected @Setter FailureHandler failureHandler;
	protected @Getter T wsSubscriber;

	public BaseWsHandler() {
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		try {
			super.handleMessage(session, message);
		} catch (Exception e) {
			log.error("handleMessage[{}] error. message: {}", name, message, e);
		}
	}

	public BaseWsHandler(String name, T wsSubscriber) {
		this.name = name;
		if (wsSubscriber instanceof DynamicWsSubscriber) {
			((DynamicWsSubscriber) wsSubscriber).setSessionSupplier(this::getSessionUtilReady);
		}
		this.wsSubscriber = wsSubscriber;
	}

	@Override
	public final void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("Connection Established[{}].", name);
		if (wsHeartbeat != null) {
			wsHeartbeat.start(session);
		}
		this.session = session;
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("Connection Closed[{}], CloseStatus={}.", name, status);
		if (wsHeartbeat != null) {
			wsHeartbeat.stop();
		}
		this.session = null;
		// normal close
		if (CloseStatus.NORMAL.equalsCode(status)) {
			log.info("Connection[{}] is normally closed.", name);
			return;
		}
		// abnormal close
		log.error("Connection[{}] is abnormally closed, CloseStatus={}.", name, status);
		if (failureHandler != null) {
			failureHandler.onAbnormalConnectionClosed(status);
		}
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("Transport Error[{}].", name, exception);
	}

	public final WebSocketSession getSessionUtilReady() {
		return this.getSessionUtilReady(3, 3);
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

}
