package io.github.xinyangpan.crypto4j.core.websocket;

import java.io.IOException;

import org.springframework.web.socket.PingMessage;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.util.AbstractHeartbeat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Heartbeat extends AbstractHeartbeat {
	// 
	protected @Getter @Setter WebSocketManager<?> webSocketManager;
	protected Subscriber subscriber;
	
	@Override
	public void start() {
		Preconditions.checkNotNull(webSocketManager);
		Preconditions.checkNotNull(subscriber);
		super.start();
	}
	
	public void start(Subscriber subscriber) {
		this.subscriber = subscriber;
		this.start();
	}

	public void stop() {
		super.stop();
		this.subscriber = null;
	}

	protected boolean keepLoop() {
		return subscriber != null && subscriber.getSession().isOpen();
	}

	protected void pingTimeout() {
		webSocketManager.getSubscriber().onPingTimeout(subscriber.getSession());
	}

	// Standard Ping, can be override for none standard. refer to okex
	protected void sendPing() throws IOException {
		log.debug("Sending Stardard ping message.");
		subscriber.sendMessage(new PingMessage(), true);
	}

}
