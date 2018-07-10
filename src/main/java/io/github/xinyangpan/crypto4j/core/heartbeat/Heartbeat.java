package io.github.xinyangpan.crypto4j.core.heartbeat;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.web.socket.WebSocketSession;

import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Heartbeat {
	// 
	private final HeartbeatHandler heartbeatHandler;
	private WebSocketSession session;
	// 
	private long interval = 30; // s
	private long timeout = 3; // s
	// 
	private LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();
	private Thread thread;

	public Heartbeat(HeartbeatHandler heartbeatHandler) {
		this.heartbeatHandler = heartbeatHandler;
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
		this.session = null;
		this.thread.interrupt();
	}

	private void run() {
		while (!thread.isInterrupted() && session.isOpen()) {
			try {
				this.queue.clear();
				long start = System.currentTimeMillis();
				this.heartbeatHandler.sendPing(session);
				Long ts = this.queue.poll(timeout, TimeUnit.SECONDS);
				if (ts != null) {
					log.debug("Ping responded in {} ms", ts - start);
					Thread.sleep(interval);
				} else {
					log.error("Ping timeout {}", System.currentTimeMillis() - start);
					heartbeatHandler.pingTimeout();
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

}
