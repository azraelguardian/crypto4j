package io.github.xinyangpan.crypto4j.exchange.okex.impl;

import java.util.function.Consumer;

import io.github.xinyangpan.crypto4j.core.WsSubscriber;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.DepthData;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.OkexWsResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.TickerData;

public interface OkexWsSubscriber extends WsSubscriber {

	/**
	 * @param symbol required 交易对 btcusdt, ethusdt, ltcusdt, etcusdt, bchusdt, ethbtc, ltcbtc, etcbtc, bchbtc...
	 * @param depth required. step0, step1, step2, step3, step4, step5（合并深度0-5）；step0时，不合并深度
	 * @param listener listener
	 */
	void depth(String symbol, int depth, Consumer<OkexWsResponse<DepthData>> listener);

	/**
	 * @param symbol required 交易对 btcusdt, ethusdt, ltcusdt, etcusdt, bchusdt, ethbtc, ltcbtc, etcbtc, bchbtc...
	 * @param 1min, 5min, 15min, 30min, 60min, 1day, 1mon, 1week, 1year
	 * @param listener listener
	 */
	void ticker(String symbol, Consumer<OkexWsResponse<TickerData>> listener);

}
