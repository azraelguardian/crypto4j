package io.github.xinyangpan.crypto4j.core.websocket.heartbeat;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.websocket.BaseWsConnector;
import io.github.xinyangpan.crypto4j.core.websocket.failurehandler.FailureHandler;
import io.github.xinyangpan.crypto4j.core.websocket.failurehandler.FailureReconnect;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WsHeartbeat {
	// 
	private @Setter FailureHandler failureHandler;
	private WebSocketSession session;
	private Thread thread;
	// 
	private @Getter @Setter long interval = 30; // s
	private @Getter @Setter long timeout = 3; // s
	// 
	private final LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();

	public WsHeartbeat(FailureHandler failureHandler) {
		this.failureHandler = failureHandler;
	}

	public WsHeartbeat(BaseWsConnector<?> wsConnector) {
		this(new FailureReconnect(wsConnector));
	}

	public void start(WebSocketSession session) {
		Preconditions.checkNotNull(session);
		Preconditions.checkState(session.isOpen());
		this.session = session;
		// 
		thread = new Thread(this::run);
		thread.start();
	}

	public void stop() {
		this.thread.interrupt();
		this.session = null;
		this.thread = null;
	}

	private void run() {
		while (!Thread.interrupted() && session != null && session.isOpen()) {
			try {
				Thread.sleep(interval * 1000);
				this.queue.clear();
				long start = System.currentTimeMillis();
				log.debug("Sending ping. sessionId={}", session.getId());
				this.sendPing(session);
				Long ts = this.queue.poll(timeout, TimeUnit.SECONDS);
				if (ts != null) {
					log.debug("Ping responded in {} ms", ts - start);
				} else {
					log.error("Ping timeout {}", System.currentTimeMillis() - start);
					if (failureHandler != null) {
						failureHandler.pingTimeout();
					}
				}
			} catch (InterruptedException e) {
				// return when interrupt, which means stop 
				return;
			} catch (Exception e) {
				log.error("Error while pinging.", e);
			}
		}
	}

	public void onPong() {
		queue.add(System.currentTimeMillis());
	}

	// standard Ping, can be override for none standard. refer to okex
	protected void sendPing(WebSocketSession session) throws IOException {
		session.sendMessage(new PingMessage());
	}

}
