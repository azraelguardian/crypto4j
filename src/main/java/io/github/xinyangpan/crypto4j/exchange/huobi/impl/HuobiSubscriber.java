package io.github.xinyangpan.crypto4j.exchange.huobi.impl;

import java.util.function.Consumer;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.subscriber.BaseDynamicWsSubscriber;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.common.HuobiWsRequest;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.depth.DepthData;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.kline.KlineData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class HuobiSubscriber extends BaseDynamicWsSubscriber {
	private Consumer<DepthData> depthListener;
	private Consumer<KlineData> klineListener;

	public void depth(String symbol, String type) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkNotNull(type);
		// 
		log.info("Subscribing marketDepth. symbol={}, type={}.", symbol, type);
		String sub = String.format("market.%s.depth.%s", symbol, type);
		String id = sub;
		this.sendTextMessage(new HuobiWsRequest(id, sub));
	}

	public void kline(String symbol, String period) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkNotNull(period);
		// 
		log.info("Subscribing kline. symbol={}, type={}.", symbol, period);
		String sub = String.format("market.%s.kline.%s", symbol, period);
		String id = sub;
		this.sendTextMessage(new HuobiWsRequest(id, sub));
	}

}
