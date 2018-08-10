package io.github.xinyangpan.crypto4j.demo.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.demo.core.KlineType;
import io.github.xinyangpan.crypto4j.demo.persist.KlinePo;
import io.github.xinyangpan.crypto4j.huobi.dto.common.HuobiWsResponse;
import io.github.xinyangpan.crypto4j.huobi.dto.market.Symbol;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.KlineRequest;
import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;
import io.github.xinyangpan.crypto4j.huobi.websocket.HuobiManager;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSubscriber;
import io.github.xinyangpan.crypto4j.huobi.websocket.impl.HuobiSync;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HuobiWsKlineProcess {
	@Autowired
	private HuobiRestService huobiRestService;
//	private String[] periods = { "1min", "5min", "15min", "30min" };
	private HuobiManager connector;
	private List<Symbol> symbols;
	
	public void start() {
		// 
		symbols = huobiRestService.symbols().fethData();
		// 
		connector = new HuobiManager();
		connector.setUrl("wss://api.huobi.pro/ws");
		connector.setSubscriber(this.buildSubscriber());
		connector.connect();
	}

	private HuobiSubscriber buildSubscriber() {
		HuobiSubscriber huobiSubscriber = new HuobiSubscriber();
		huobiSubscriber.setKlineResponse(this::onKline);
		huobiSubscriber.setConnectedListener(this::onConnected);
		huobiSubscriber.setHuobiSync(new HuobiSync());
		return huobiSubscriber;
	}
	
	public void onConnected (WebSocketSession session) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HuobiSubscriber huobiSubscriber = connector.getSubscriber();
		for (Symbol symbol : symbols) {
			KlineRequest klineRequest = new KlineRequest(symbol.getSymbol(), "1min");
			klineRequest.setFrom(ZonedDateTime.now().minusMinutes(2).toEpochSecond());
			klineRequest.setTo(ZonedDateTime.now().toEpochSecond());
			try {
				this.onKline(huobiSubscriber.requestSync(klineRequest));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void onKline(HuobiWsResponse<Kline> huobiWsResponse) {
		log.info("{}", huobiWsResponse);
		String[] split = huobiWsResponse.getRep().split("\\.");
		String symbol = split[1];
		KlineType klineType = getKlineType(split[3]);
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
