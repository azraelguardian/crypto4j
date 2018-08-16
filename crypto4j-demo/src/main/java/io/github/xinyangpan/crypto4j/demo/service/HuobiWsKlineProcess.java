package io.github.xinyangpan.crypto4j.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.demo.core.KlineType;
import io.github.xinyangpan.crypto4j.demo.persist.KlinePo;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsSubMsg;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import io.github.xinyangpan.crypto4j.huobi.websocket.HuobiManager;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSubscriber;

@Service
public class HuobiWsKlineProcess {
	@Autowired
	private HuobiService huobiService;
	@Autowired
	private KlinePersistService klinePersistService;
	//	private String[] periods = { "1min", "5min", "15min", "30min" };
	private HuobiManager connector;

	public void start() {
		HuobiSubscriber huobiSubscriber = this.buildSubscriber();
		// 
		connector = new HuobiManager();
		connector.setUrl("wss://api.huobi.pro/ws");
		connector.setSubscriber(huobiSubscriber);
		connector.connect();
		// 
		huobiSubscriber.setAbnormalConnectionClosedListener(closeStatus -> connector.reconnect());
	}

	private HuobiSubscriber buildSubscriber() {
		HuobiSubscriber huobiSubscriber = new HuobiSubscriber();
		huobiSubscriber.setKlineListener(this::onKline);
		huobiSubscriber.setConnectedListener(this::onConnected);
		return huobiSubscriber;
	}

	public void onConnected(WebSocketSession session) {
		HuobiSubscriber huobiSubscriber = connector.getSubscriber();
		for (String symbol : huobiService.getSymbols()) {
			huobiSubscriber.kline(symbol, "1min");
		}
	}

	private void onKline(HuobiWsSubMsg<Kline> huobiWsResponse) {
		String[] split = huobiWsResponse.getCh().split("\\.");
		String symbol = split[1];
		KlineType klineType = getKlineType(split[3]);
		Kline kline = huobiWsResponse.getTick();
		//
		KlinePo klinePo = new KlinePo();
		klinePo.setExchange("huobi");
		klinePo.setSymbol(symbol);
		klinePo.setType(klineType);
		klinePo.setOpenTime(kline.getOpenTime());
		klinePo.setClose(kline.getClose());
		klinePo.setVolume(kline.getAmount());
		klinePo.setHigh(kline.getHigh());
		klinePo.setId(kline.getId());
		klinePo.setLow(kline.getLow());
		klinePo.setOpen(kline.getOpen());
		klinePo.setQuoteAssetVolume(kline.getVol());
		klinePo.setNumberOfTrades(kline.getCount());
		klinePo.calculateChange();
		klinePersistService.onKlinePo(klinePo);
	}

	private KlineType getKlineType(String type) {
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
