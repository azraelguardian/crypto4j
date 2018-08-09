package io.github.xinyangpan.crypto4j.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.demo.core.KlineType;
import io.github.xinyangpan.crypto4j.demo.persist.KlinePo;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiRestResponse;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsSubMsg;
import io.github.xinyangpan.crypto4j.huobi.dto.market.Symbol;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;
import io.github.xinyangpan.crypto4j.huobi.websocket.HuobiManager;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSubscriber;

@Service
public class HuobiWsKlineProcess {
	@Autowired
	private HuobiRestService huobiRestService;
	private String[] periods = { "1min", "5min", "15min", "30min" };

	public void start() {
		// 
		HuobiManager connector = new HuobiManager();
		connector.setUrl("wss://api.huobi.pro/ws");
		connector.setSubscriber(this.buildSubscriber());
		connector.connect();
	}

	private HuobiSubscriber buildSubscriber() {
		HuobiRestResponse<List<Symbol>> response = huobiRestService.symbols();
		List<Symbol> symbols = response.getData();
		HuobiSubscriber huobiSubscriber = new HuobiSubscriber();
		huobiSubscriber.setDepthListener(Crypto4jUtils.noOp());
		huobiSubscriber.setTickerListener(Crypto4jUtils.noOp());
		huobiSubscriber.setKlineListener(this::onKline);
		for (Symbol symbol : symbols) {
			for (String period : periods) {
				huobiSubscriber.kline(symbol.getSymbol(), period);
			}
		}
		return huobiSubscriber;
	}

	private void onKline(HuobiWsSubMsg<Kline> huobiWsResponse) {
		String symbol = huobiWsResponse.toSymbol();
		KlineType klineType = getKlineType(huobiWsResponse);
		Kline kline = huobiWsResponse.getTick();
		//
		KlinePo klinePo = new KlinePo();
		klinePo.setExchange("huobi");
		klinePo.setSymbol(symbol);
		klinePo.setType(klineType);
		klinePo.setOpenTime(klinePo.getOpenTime());
		klinePo.setClose(kline.getClose());
		klinePo.setVolume(kline.getAmount());
		klinePo.setHigh(kline.getHigh());
		klinePo.setId(kline.getId());
		klinePo.setLow(kline.getLow());
		klinePo.setOpen(kline.getOpen());
		klinePo.setQuoteAssetVolume(kline.getVol());
		klinePo.setNumberOfTrades(kline.getCount());
		klinePo.calculateChange();
		System.out.println(klinePo);
	}

	private KlineType getKlineType(HuobiWsSubMsg<Kline> huobiWsResponse) {
		String type = huobiWsResponse.getCh().split("\\.")[3];
		switch (type) {
		case "1min":
			return KlineType.K1M;
		case "5min":
			return KlineType.K5M;
		case "15min":
			return KlineType.K15M;
		case "30min":
			return KlineType.K30M;
		default:
			throw new IllegalArgumentException();
		}
	}

}
