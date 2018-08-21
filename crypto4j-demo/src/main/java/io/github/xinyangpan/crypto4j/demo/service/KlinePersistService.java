package io.github.xinyangpan.crypto4j.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Service;

import io.github.xinyangpan.crypto4j.demo.persist.KlineDao;
import io.github.xinyangpan.crypto4j.demo.persist.KlinePo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KlinePersistService implements Lifecycle {
	@Autowired
	private KlineDao klineDao;
	// symbol -> po
	private ConcurrentHashMap<String, KlinePo> symbolToPoMap = new ConcurrentHashMap<String, KlinePo>();
	private List<KlinePo> klinePos = new ArrayList<>();
	private Thread thread;

	@PostConstruct
	@Override
	public void start() {
		log.info("Starting.");
		thread = new Thread(() -> {
			while (!thread.isInterrupted()) {
				persist();
			}
		});
		thread.start();
	}

	@Override
	public void stop() {
		thread.interrupt();
	}

	@Override
	public boolean isRunning() {
		return thread.isAlive();
	}

	public void onKlinePo(KlinePo klinePo) {
		KlinePo old = symbolToPoMap.put(klinePo.getSymbol(), klinePo);
		if (old == null) {
			return;
		}
		if (old.getOpenTime().isBefore(klinePo.getOpenTime())) {
			this.addToCache(old);
		}
	}

	private synchronized void addToCache(KlinePo klinePo) {
		klinePos.add(klinePo);
		if (klinePos.size() >= 100) {
			this.notify();
		}
	}

	private synchronized void persist() {
		try {
			this.wait(1000 * 5);
		} catch (InterruptedException e) {}
		if (klinePos.isEmpty()) {
			log.debug("Persisting: Cache is empty.");
			return;
		}
		log.info("Persisting: {} records.", klinePos.size());
		klineDao.saveAll(klinePos);
		klinePos.clear();
	}

}
