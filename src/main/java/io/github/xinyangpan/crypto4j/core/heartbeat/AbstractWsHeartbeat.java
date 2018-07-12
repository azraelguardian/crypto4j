package io.github.xinyangpan.crypto4j.core.heartbeat;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractWsHeartbeat {
	// 
	private WebSocketSession session;
	private Thread thread;
	// 
	private long interval = 30; // s
	private long timeout = 3; // s
	// 
	private final LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();

	public AbstractWsHeartbeat() {
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
					this.pingTimeout();
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

	protected void sendPing(WebSocketSession session) throws IOException {
		session.sendMessage(new PingMessage());
	}

	protected abstract void pingTimeout();

}
