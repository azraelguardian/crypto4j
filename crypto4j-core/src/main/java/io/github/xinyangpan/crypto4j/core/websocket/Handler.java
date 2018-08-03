package io.github.xinyangpan.crypto4j.core.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Handler extends AbstractWebSocketHandler {
	protected @Getter WebSocketSession session;
	protected @Setter WebSocketManager<?> webSocketManager;

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		try {
			super.handleMessage(session, message);
		} catch (Exception e) {
			log.error("handleMessage[{}] error. message: {}", this.getName(), message, e);
		}
	}

	@Override
	public final void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("Connection Established[{}].", this.getName());
		this.session = session;
		Heartbeat heartbeat = webSocketManager.getHeartbeat();
		if (heartbeat != null) {
			heartbeat.start(session);
			log.info("Hearbeat Started[{}].", this.getName());
		}
		webSocketManager.getSubscriber().onConnected(session);;
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.debug("Connection Closed[{}], CloseStatus={}.", this.getName(), status);
		Heartbeat heartbeat = webSocketManager.getHeartbeat();
		if (heartbeat != null) {
			heartbeat.stop();
		}
		this.session = null;
		// normal close
		if (CloseStatus.NORMAL.equalsCode(status)) {
			log.info("Connection[{}] is normally closed.", this.getName());
			return;
		}
		// abnormal close
		log.error("Connection[{}] is abnormally closed, CloseStatus={}.", this.getName(), status);
		webSocketManager.getSubscriber().onAbnormalConnectionClosed(status);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("Transport Error[{}].", this.getName(), exception);
	}

	protected void onPong(String msg) {
		log.debug("Pond recieved. msg: {}", msg);
		Heartbeat heartbeat = webSocketManager.getHeartbeat();
		if (heartbeat != null) {
			heartbeat.onPong();
		}
	}

	private String getName() {
		return this.webSocketManager.getName();
	}

}
