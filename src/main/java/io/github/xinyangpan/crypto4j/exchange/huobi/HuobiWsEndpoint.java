package io.github.xinyangpan.crypto4j.exchange.huobi;

import java.util.function.Consumer;

import io.github.xinyangpan.crypto4j.exchange.huobi.dto.marketdepth.MarketDepthData;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.tradedetail.TradeDetailData;

public interface HuobiWsEndpoint {
	
	/**
	 * @param symbol required 交易对 btcusdt, ethusdt, ltcusdt, etcusdt, bchusdt, ethbtc, ltcbtc, etcbtc, bchbtc...
	 * @param type required. step0, step1, step2, step3, step4, step5（合并深度0-5）；step0时，不合并深度
	 * @param listener listener
	 */
	void marketDepth(String symbol, String type, Consumer<MarketDepthData> listener);

	/**
	 * @param symbol required 交易对 btcusdt, ethusdt, ltcusdt, etcusdt, bchusdt, ethbtc, ltcbtc, etcbtc, bchbtc...
	 * @param listener listener
	 */
	void tradeDetail(String symbol, Consumer<TradeDetailData> listener);
	
}
