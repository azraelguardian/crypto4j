package io.github.xinyangpan.crypto4j.core.heartbeat;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.BaseWsHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeat {
	// 
	private final BaseWsHandler wsHandler;
	private final HeartBeatHandler heartBeatHandler;
	// 
	private long interval = 30; // s
	private long timeout = 3; // s
	// 
	private LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();
	private Thread thread = new Thread(this::run);

	public HeartBeat(BaseWsHandler wsHandler, HeartBeatHandler heartBeatHandler) {
		this.wsHandler = wsHandler;
		this.heartBeatHandler = heartBeatHandler;
	}

	public void start() {
		Preconditions.checkNotNull(wsHandler);
		Preconditions.checkState(wsHandler.getSession().isOpen());
		thread.start();
	}

	public void stop() {
		thread.interrupt();
	}

	private void run() {
		while (!thread.isInterrupted() && wsHandler.getSession().isOpen()) {
			try {
				this.queue.clear();
				long start = System.currentTimeMillis();
				this.heartBeatHandler.sendPing(wsHandler.getSession());
				Long ts = this.queue.poll(timeout, TimeUnit.SECONDS);
				if (ts != null) {
					log.debug("Ping responded in {} ms", ts - start);
					Thread.sleep(interval);
				} else {
					log.error("Ping timeout {}", System.currentTimeMillis() - start);
					heartBeatHandler.pingTimeout();
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
