package io.github.xinyangpan.crypto4j.huobi.websocket.impl;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsRequest;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsResponse;

public class HuobiSync implements Consumer<HuobiWsResponse<?>> {
	private Map<String, CompletableFuture<HuobiWsResponse<?>>> id2Future = new ConcurrentHashMap<>();
	private int maxCount = 15;
	private int count;
	
	public CompletableFuture<HuobiWsResponse<?>> request(HuobiWsRequest huobiWsRequest) {
		CompletableFuture<HuobiWsResponse<?>> completableFuture = new CompletableFuture<>();
		id2Future.put(huobiWsRequest.getId(), completableFuture);
		this.tryLock();
		return completableFuture;
	}

	@Override
	public void accept(HuobiWsResponse<?> huobiWsResponse) {
		this.releaseLock();
		CompletableFuture<HuobiWsResponse<?>> completableFuture = id2Future.get(huobiWsResponse.getId());
		completableFuture.complete(huobiWsResponse);
	}
	
	private synchronized void tryLock() {
		if (count >= maxCount) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		count ++;
	}
	
	private synchronized void releaseLock() {
		count --;
		this.notify();
	}
	
}
