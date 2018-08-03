package io.github.xinyangpan.crypto4j.huobi.websocket.impl;

import java.util.function.Consumer;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.core.websocket.Subscriber;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsRequest;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsResponse;
import io.github.xinyangpan.crypto4j.huobi.dto.market.depth.Depth;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import io.github.xinyangpan.crypto4j.huobi.dto.market.tick.Ticker;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuobiSubscriber extends Subscriber {
	private @Getter @Setter Consumer<HuobiWsResponse<Depth>> depthListener = Crypto4jUtils.logConsumer();
	private @Getter @Setter Consumer<HuobiWsResponse<Kline>> klineListener = Crypto4jUtils.logConsumer();
	private @Getter @Setter Consumer<HuobiWsResponse<Ticker>> tickerListener = Crypto4jUtils.logConsumer();

	public void depth(String symbol, String type) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkNotNull(type);
		// 
		log.info("Subscribing marketDepth. symbol={}, type={}.", symbol, type);
		String sub = String.format("market.%s.depth.%s", symbol, type);
		String id = sub;
		this.subscribe(new HuobiWsRequest(id, sub));
	}

	public void kline(String symbol, String period) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkNotNull(period);
		// 
		log.info("Subscribing kline. symbol={}, type={}.", symbol, period);
		String sub = String.format("market.%s.kline.%s", symbol, period);
		String id = sub;
		this.subscribe(new HuobiWsRequest(id, sub));
	}

	public void ticker(String symbol) {
		// 
		Preconditions.checkNotNull(symbol);
		// 
		log.info("Subscribing ticker. symbol={}.", symbol);
		String sub = String.format("market.%s.detail", symbol);
		String id = sub;
		this.subscribe(new HuobiWsRequest(id, sub));
	}

	public void onDepthData(HuobiWsResponse<Depth> response) {
		depthListener.accept(response);
	}

	public void onKlineData(HuobiWsResponse<Kline> response) {
		klineListener.accept(response);
	}

	public void onTickData(HuobiWsResponse<Ticker> response) {
		tickerListener.accept(response);
	}

}
