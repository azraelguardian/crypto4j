package io.github.xinyangpan.crypto4j.exchange.okex;

import java.util.function.Consumer;

import io.github.xinyangpan.crypto4j.exchange.huobi.dto.depth.DepthData;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.kline.KlineData;

public interface OkexWsSubscriber {
	
	/**
	 * @param symbol required 交易对 btcusdt, ethusdt, ltcusdt, etcusdt, bchusdt, ethbtc, ltcbtc, etcbtc, bchbtc...
	 * @param type required. step0, step1, step2, step3, step4, step5（合并深度0-5）；step0时，不合并深度
	 * @param listener listener
	 */
	void marketDepth(String symbol, String type, Consumer<DepthData> listener);

	/**
	 * @param symbol required 交易对 btcusdt, ethusdt, ltcusdt, etcusdt, bchusdt, ethbtc, ltcbtc, etcbtc, bchbtc...
	 * @param 1min, 5min, 15min, 30min, 60min, 1day, 1mon, 1week, 1year
	 * @param listener listener
	 */
	void kline(String symbol, String period, Consumer<KlineData> listener);
	
}
