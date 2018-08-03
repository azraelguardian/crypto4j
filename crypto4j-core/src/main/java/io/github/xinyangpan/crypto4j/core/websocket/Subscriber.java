package io.github.xinyangpan.crypto4j.core.websocket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Subscriber {
	protected WebSocketManager webSocketManager;
	protected Consumer<WebSocketSession> connectedListener = Crypto4jUtils.noOp();
	protected Consumer<WebSocketSession> pingTimeoutListener = Crypto4jUtils.noOp();
	protected Consumer<CloseStatus> abnormalConnectionClosedListener = Crypto4jUtils.noOp();
	protected List<Object> subRequests = new ArrayList<>();

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
