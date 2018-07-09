package io.github.xinyangpan.crypto4j.exchange.huobi.impl;

import java.util.function.Consumer;

import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiWsEndpoint;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.marketdepth.MarketDepthData;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.tradedetail.TradeDetailData;

public class HuobiWsEndpointImpl implements HuobiWsEndpoint {

	@Override
	public void marketDepth(String symbol, String type, Consumer<MarketDepthData> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tradeDetail(String symbol, Consumer<TradeDetailData> listener) {
		// TODO Auto-generated method stub
		
	}

}
