package io.github.xinyangpan.crypto4j.core;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketHandler extends AbstractWebSocketHandler {
	protected @Getter @Setter String name;
	protected @Getter WebSocketSession session;

	public WebSocketHandler() {
	}

	public WebSocketHandler(String name) {
		this.name = name;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("Connection Established[{}].", name);
		this.session = session;
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("Connection Closed[{}], CloseStatus={}.", name, status);
		this.session = null;
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("Transport Error[{}].", name, exception);
	}

}
