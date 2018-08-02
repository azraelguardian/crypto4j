package io.github.xinyangpan.crypto4j.okex.websocket.impl;

import java.util.function.Consumer;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.core.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.core.websocket.subscriber.BaseDynamicWsSubscriber;
import io.github.xinyangpan.crypto4j.okex.dto.common.OkexWsRequest;
import io.github.xinyangpan.crypto4j.okex.dto.common.OkexWsResponse;
import io.github.xinyangpan.crypto4j.okex.dto.market.Depth;
import io.github.xinyangpan.crypto4j.okex.dto.market.TickerData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class OkexWsSubscriber extends BaseDynamicWsSubscriber {
	private Consumer<OkexWsResponse<Depth>> depthListener = Crypto4jUtils.logConsumer();
	private Consumer<OkexWsResponse<TickerData>> tickerListener = Crypto4jUtils.logConsumer();

	public void depth(String symbol, int depth) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkArgument(depth >= 0);
		// 
		log.info("Subscribing marketDepth. symbol={}, type={}.", symbol, depth);
		String channel = String.format("ok_sub_spot_%s_depth_%s", symbol, depth);
		this.sendTextMessage(OkexWsRequest.addChannel(channel));
	}

	public void ticker(String symbol) {
		// 
		Preconditions.checkNotNull(symbol);
		// 
		log.info("Subscribing ticker. symbol={}.", symbol);
		String channel = String.format("ok_sub_spot_%s_ticker", symbol);
		this.sendTextMessage(OkexWsRequest.addChannel(channel));
	}

}
