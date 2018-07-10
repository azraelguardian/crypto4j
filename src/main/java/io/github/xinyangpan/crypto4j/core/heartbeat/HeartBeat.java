package io.github.xinyangpan.crypto4j.core.heartbeat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.common.base.Preconditions;

public abstract class HeartBeat {
	private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	private long interval = 30; // s
	private long checkInterval = 1000; // ms
	private AtomicBoolean acked = new AtomicBoolean(true);
	protected WebSocketSession session;

	public static HeartBeat defaultPing() {
		return new HeartBeat() {
			@Override
			protected void sendPing() throws Exception {
				session.sendMessage(new PingMessage());
			}
		};
	}

	public static HeartBeat withPingAs(Runnable runnable) {
		return new HeartBeat() {
			@Override
			protected void sendPing() throws Exception {
				runnable.run();
			}
		};
	}

	public void start() {
		Preconditions.checkNotNull(session);
		Preconditions.checkState(session.isOpen());
		// 
		executorService.schedule(this::run, interval, TimeUnit.SECONDS);
	}

	private void run() {
		try {
			while (session.isOpen()) {
				this.waitUntilAcked();
				this.acked.set(false);
				this.sendPing();
				Thread.sleep(interval);
			}
		} catch (Exception e) {
		}
	}

	private void waitUntilAcked() throws InterruptedException {
		while (!acked.get()) {
			Thread.sleep(checkInterval);
		}
	}

	protected abstract void sendPing() throws Exception;

	public void onPong() {
		this.acked.set(true);
	}

}
