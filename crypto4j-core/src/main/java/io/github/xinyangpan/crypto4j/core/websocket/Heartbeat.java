package io.github.xinyangpan.crypto4j.core.websocket;

import java.io.IOException;

import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.util.AbstractHeartbeat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Heartbeat extends AbstractHeartbeat {
	// 
	protected @Getter @Setter WebSocketManager webSocketManager;
	protected WebSocketSession session;
	
	@Override
	public void start() {
		Preconditions.checkNotNull(webSocketManager);
		Preconditions.checkNotNull(session);
		Preconditions.checkState(session.isOpen());
		super.start();
	}
	
	public void start(WebSocketSession session) {
		this.session = session;
		this.start();
	}

	public void stop() {
		super.stop();
		this.session = null;
	}

	protected boolean keepLoop() {
		return session != null && session.isOpen();
	}

	protected void pingTimeout() {
		webSocketManager.getSubscriber().onPingTimeout(session);
	}

	// standard Ping, can be override for none standard. refer to okex
	protected void sendPing() throws IOException {
		log.info("Sending Stardard ping message.");
		session.sendMessage(new PingMessage());
	}

}
