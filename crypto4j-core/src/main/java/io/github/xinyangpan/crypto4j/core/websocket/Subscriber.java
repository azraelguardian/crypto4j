package io.github.xinyangpan.crypto4j.core.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Subscriber extends AbstractWebSocketHandler {
	private static final Logger log = LoggerFactory.getLogger(Subscriber.class);
	protected static Marker MSG_TRACK = MarkerFactory.getMarker("msg_track");
	// 
	protected @Setter(AccessLevel.PACKAGE) WebSocketManager<?> webSocketManager;
	protected @Getter WebSocketSession session;
	protected final ObjectMapper objectMapper = new ObjectMapper();
	private List<WebSocketMessage<?>> messages = new ArrayList<>();
	// 
	protected @Getter @Setter Consumer<WebSocketSession> connectedListener = Crypto4jUtils.noOp();
	protected @Getter @Setter Consumer<WebSocketSession> pingTimeoutListener = Crypto4jUtils.noOp();
	protected @Getter @Setter Consumer<CloseStatus> abnormalConnectionClosedListener = Crypto4jUtils.noOp();

	protected String getName() {
		return this.webSocketManager.getName();
	}

	// -----------------------------
	// ----- INBOUND
	// -----------------------------

	public void send(Object message) {
		try {
			String json = objectMapper.writeValueAsString(message);
			log.debug("Sending json: {}", json);
			this.sendMessage(new TextMessage(json));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void sendMessage(WebSocketMessage<?> message) throws IOException {
		this.sendMessage(message, false);
	}

	public synchronized void sendMessage(WebSocketMessage<?> message, boolean direct) throws IOException {
		log.info("Sending message. direct: {}, message: {}", direct, message);
		if (direct) {
			Preconditions.checkNotNull(session, "Session is null.");
			Preconditions.checkArgument(session.isOpen(), "Session is closed.");
			session.sendMessage(message);
			return;
		}
		if (session != null && session.isOpen()) {
			session.sendMessage(message);
		} else {
			messages.add(message);
		}
	}

	private void sendCacheMessage() {
		for (WebSocketMessage<?> webSocketMessage : messages) {
			try {
				this.sendMessage(webSocketMessage, true);
			} catch (IOException e) {
				log.error("Error when sending msg, will not propagate.", e);
			}
		}
	}

	// -----------------------------
	// ----- OUTBOUND
	// -----------------------------

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
			heartbeat.start(this);
			log.info("Hearbeat Started[{}].", this.getName());
		}
		this.sendCacheMessage();
		connectedListener.accept(session);
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
		abnormalConnectionClosedListener.accept(status);
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
		} else {
			log.warn("No heartbeat is set. msg: {}", msg);
		}
	}

	protected void onPingTimeout(WebSocketSession session) {
		log.error("pingTimeout");
		pingTimeoutListener.accept(session);
	}

	protected void unhandledMessage(Object obj) {
		log.warn("Unhandled Message: {}", obj, new RuntimeException());
	}

}
