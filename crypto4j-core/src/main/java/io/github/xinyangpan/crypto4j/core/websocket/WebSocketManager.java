package io.github.xinyangpan.crypto4j.core.websocket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.websocket.failurehandler.FailureHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class WebSocketManager<S extends Subscriber> {
	private WebSocketConnectionManager manager;
	protected @Getter @Setter String url;
	protected @Getter @Setter String name;
	protected @Getter @Setter Heartbeat heartbeat;
	protected @Getter @Setter S subscriber;
	private Thread sessionChecker;

	public void connect() {
		Preconditions.checkNotNull(subscriber);
		subscriber.setWebSocketManager(this);
		if (heartbeat != null) {
			heartbeat.setWebSocketManager(this);
		}
		// 
		this.manager = createConnectionManager(url, subscriber);
		log.info("WebSocketManager[{}] is connecting to {}.", name, url);
		manager.start();
		sessionChecker = new Thread(this::checkSession);
		sessionChecker.setDaemon(true);
		sessionChecker.start();
	}

	private void checkSession() {
		try {
			Thread.sleep(5 * 1000);
			WebSocketSession session = subscriber.getSession();
			if (session != null && session.isOpen()) {
				log.info("Session[{}] check passed.", name);
				return;
			}
			log.error("Session[{}] check failed.", name);
			new Thread(this::reconnect).start();
		} catch (InterruptedException e) {
			log.info("Session[{}] check Interrupted.", name);
		}
	}

	public void disconnect() {
		if (sessionChecker != null) {
			sessionChecker.interrupt();
		}
		if (manager == null) {
			return;
		}
		manager.stop();
		manager = null;
	}

	public boolean isConnected() {
		WebSocketSession session = subscriber.getSession();
		return session != null && subscriber.getSession().isOpen();
	}

	public void reconnect() {
		log.info("try to reconnect...");
		this.disconnect();
		this.connect();
	}

	private WebSocketConnectionManager createConnectionManager(String url, S wsHandler) {
		StandardWebSocketClient client = new StandardWebSocketClient();
		return new WebSocketConnectionManager(client, wsHandler, url);
	}

	public void setFailureHandler(FailureHandler failureHandler) {
		subscriber.setAbnormalConnectionClosedListener(failureHandler::onAbnormalConnectionClosed);
		subscriber.setPingTimeoutListener(failureHandler::pingTimeout);
	}

}
