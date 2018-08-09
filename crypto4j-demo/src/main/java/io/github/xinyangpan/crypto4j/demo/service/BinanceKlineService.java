package io.github.xinyangpan.crypto4j.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.xinyangpan.crypto4j.binance.dto.rest.market.Kline;
import io.github.xinyangpan.crypto4j.binance.dto.rest.market.KlineParam;
import io.github.xinyangpan.crypto4j.binance.rest.BinanceRestService;
import io.github.xinyangpan.crypto4j.demo.persist.KlineDao;
import io.github.xinyangpan.crypto4j.demo.persist.KlinePo;

@Service
public class BinanceKlineService {
	@Autowired
	private KlineDao klineDao;
	@Autowired
	private BinanceRestService binanceRestService;
	
	public void save() {
		// 
		KlineParam klineParam = new KlineParam();
		klineParam.setSymbol("BTCUSDT");
		klineParam.setInterval("1m");
		klineParam.setLimit(1000);
		List<Kline> klines = binanceRestService.kline(klineParam);
		List<KlinePo> klinePos = klines.stream().map(this::buildKlinePo).collect(Collectors.toList());
		klineDao.saveAll(klinePos);
	}

	public KlinePo buildKlinePo(Kline kline) {
		if (kline == null) {
			return null;
		}
		KlinePo klinePo = new KlinePo();
		klinePo.setLow(kline.getLow());
		klinePo.setHigh(kline.getHigh());
		klinePo.setClose(kline.getClose());
		klinePo.setOpenTime(kline.getOpenTime());
		klinePo.setVolume(kline.getVolume());
		klinePo.setOpen(kline.getOpen());
		klinePo.setNumberOfTrades(kline.getNumberOfTrades());
		klinePo.setQuoteAssetVolume(kline.getQuoteAssetVolume());
		klinePo.calculateChange();
		return klinePo;
	}
	
}
