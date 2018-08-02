package io.github.xinyangpan.crypto4j.core.websocket;

import static io.github.xinyangpan.crypto4j.core.Crypto4jUtils.objectMapper;

import org.springframework.util.Assert;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.websocket.failurehandler.FailureHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class WebSocketManager {
	private WebSocketConnectionManager manager;
	protected @Getter @Setter String url;
	protected @Getter @Setter String name;
	protected Handler handler;
	protected @Getter @Setter Heartbeat heartbeat;
	protected @Getter @Setter Subscriber subscriber;

	public void connect() {
		Preconditions.checkNotNull(handler);
		Preconditions.checkNotNull(subscriber);
		// 
		handler.setWebSocketManager(this);
		subscriber.setWebSocketManager(this);
		if (heartbeat != null) {
			heartbeat.setWebSocketManager(this);
		}
		// 
		this.manager = createConnectionManager(url, handler);
		log.info("WebSocketManager[{}] is connecting to {}.", name, url);
		manager.start();
	}

	public void connectSync() {
		this.connect();
		handler.getSessionUtilReady();
	}

	public void disconnect() {
		if (manager == null) {
			return;
		}
		manager.stop();
		manager = null;
	}

	public boolean isConnected() {
		WebSocketSession session = handler.getSession();
		return session != null && handler.getSession().isOpen();
	}

	public void reconnect() {
		log.info("try to reconnect...");
		this.disconnect();
		this.connect();
	}

	protected WebSocketConnectionManager createConnectionManager(String url, Handler wsHandler) {
		StandardWebSocketClient client = new StandardWebSocketClient();
		return new WebSocketConnectionManager(client, wsHandler, url);
	}

	public void sendInJson(Object message) {
		try {
			WebSocketSession webSocketSession = this.handler.getSession();
			Assert.state(webSocketSession != null, "Session is null.");
			webSocketSession.sendMessage(new TextMessage(objectMapper().writeValueAsString(message)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setFailureHandler(FailureHandler failureHandler) {
		subscriber.setAbnormalConnectionClosedListener(failureHandler::onAbnormalConnectionClosed);
		subscriber.setPingTimeoutListener(failureHandler::pingTimeout);
	}

}
