package io.github.xinyangpan.crypto4j.core.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Subscriber extends AbstractWebSocketHandler {
	private static final Logger log = LoggerFactory.getLogger(Subscriber.class);
	// 
	protected @Setter(AccessLevel.PACKAGE) WebSocketManager<?> webSocketManager;
	private List<Object> subRequests = new ArrayList<>();
	protected @Getter WebSocketSession session;
	// 
	protected @Getter @Setter Consumer<WebSocketSession> connectedListener = Crypto4jUtils.noOp();
	protected @Getter @Setter Consumer<WebSocketSession> pingTimeoutListener = Crypto4jUtils.noOp();
	protected @Getter @Setter Consumer<CloseStatus> abnormalConnectionClosedListener = Crypto4jUtils.noOp();

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		try {
			super.handleMessage(session, message);
		} catch (Exception e) {
			log.error("handleMessage[{}] error. message: {}", this.getName(), message, e);
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
		log.debug("{}: Pond recieved. msg: {}", this.getName(), message);
		this.onPong("Standard Pong");
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

	protected String getName() {
		return this.webSocketManager.getName();
	}
	
	public void onConnected(WebSocketSession session) {
		connectedListener.accept(session);
		this.sendSubRequests();
	}

	public void onPingTimeout(WebSocketSession session) {
		log.error("pingTimeout");
		pingTimeoutListener.accept(session);
	}

	public void onAbnormalConnectionClosed(CloseStatus status) {
		log.error("onAbnormalConnectionClosed: {}", status);
		abnormalConnectionClosedListener.accept(status);
	}

	public void unhandledMessage(Object obj) {
		log.warn("Unhandled Message: {}", obj, new RuntimeException());
	}

	protected void subscribe(Object sub) {
		if (webSocketManager != null && webSocketManager.isConnected()) {
			this.sendSubRequest(sub);
		} else {
			this.subRequests.add(sub);
		}
	}

	private void sendSubRequests() {
		for (Object object : subRequests) {
			sendSubRequest(object);
		}
	}

	private void sendSubRequest(Object object) {
		try {
			log.info("Sending Sub Request: {}", object);
			webSocketManager.sendInJson(object);
		} catch (Exception e) {
			log.error("Error when sending: {}", object, e);
		}
	}

}
