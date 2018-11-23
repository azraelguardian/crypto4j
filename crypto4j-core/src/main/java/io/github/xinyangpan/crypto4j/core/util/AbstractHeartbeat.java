package io.github.xinyangpan.crypto4j.core.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractHeartbeat {
	private static final Logger log = LoggerFactory.getLogger(AbstractHeartbeat.class);

	// 
	private Thread thread;
	// 
	private @Getter @Setter long interval = 30; // s
	private @Getter @Setter long timeout = 3; // s
	private @Getter @Setter String name = this.getClass().getSimpleName();
	// 
	private final LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();

	public synchronized void start() {
		// 
		thread = new Thread(this::run);
		thread.setDaemon(true);
		thread.start();
	}

	public synchronized void stop() {
		if (this.thread == null) {
			return;
		}
		this.thread.interrupt();
		this.thread = null;
	}

	private void run() {
		while (!Thread.interrupted() && keepLoop()) {
			try {
				Thread.sleep(interval * 1000);
				this.queue.clear();
				long start = System.currentTimeMillis();
				log.debug("{}: Sending ping.", name);
				this.sendPing();
				Long ts = this.queue.poll(timeout, TimeUnit.SECONDS);
				if (ts != null) {
					log.debug("{}: Ping responded in {} ms", name, ts - start);
				} else {
					log.error("{}: Ping timeout {}", name, System.currentTimeMillis() - start);
					pingTimeout();
				}
			} catch (InterruptedException e) {
				// return when interrupt, which means stop 
				return;
			} catch (Exception e) {
				log.error("{}: Error while pinging.", name, e);
			}
		}
		log.info("{}: Heartbeat stopped.", name);
	}

	public void onPong() {
		queue.add(System.currentTimeMillis());
	}

	protected abstract boolean keepLoop();

	protected abstract void pingTimeout();

	protected abstract void sendPing() throws Exception;

}
