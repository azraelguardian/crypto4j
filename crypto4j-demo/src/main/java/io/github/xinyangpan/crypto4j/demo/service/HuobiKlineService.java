package io.github.xinyangpan.crypto4j.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Stopwatch;

import io.github.xinyangpan.crypto4j.demo.core.KlineType;
import io.github.xinyangpan.crypto4j.demo.persist.KlineDao;
import io.github.xinyangpan.crypto4j.demo.persist.KlinePo;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.KlineParam;
import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;

@Service
public class HuobiKlineService {
	@Autowired
	private KlineDao klineDao;
	@Autowired
	private HuobiRestService huobiRestService;
	//
	private List<String> symbols;

	//	@Scheduled(cron = "15 * * * * *")
	public void everyMinute() throws InterruptedException, ExecutionException {
		List<KlineType> typeTypes = KlineType.getKlineTypeTypes(LocalDateTime.now());
		for (KlineType klineType : typeTypes) {
			this.save(klineType);
		}
	}

	public void save(KlineType klineType) throws InterruptedException, ExecutionException {
		Stopwatch stopwatch = Stopwatch.createStarted();
		//		List<KlinePo> klinePos = symbols.stream()//
		//			.map(symbol -> {
		//				try {
		//					KlineParam klineParam = KlineParam.builder()//
		//						.period(klineType.getHuobiCode()).symbol(symbol).size(2)//
		//						.build();
		//					List<Kline> klines = huobiRestService.kline(klineParam).fethData();
		//					Kline kline = klines.get(1);
		//					System.out.println(stopwatch.elapsed());
		//					return buildKlinePo(kline, symbol, klineType);
		//				} catch (Exception e) {
		//					// TODO Auto-generated catch block
		//					e.printStackTrace();
		//					System.err.println(symbol);
		//					return null;
		//				}
		//			})//
		//			.collect(Collectors.toList());
		// 
		ForkJoinPool forkJoinPool = new ForkJoinPool(3);
		List<KlinePo> klinePos = forkJoinPool.submit(() -> {
			return symbols.parallelStream()//
				.map(symbol -> {
					try {
						KlineParam klineParam = KlineParam.builder()//
							.period(klineType.getHuobiCode()).symbol(symbol).size(2)//
							.build();
						List<Kline> klines = huobiRestService.kline(klineParam).fethData();
						Kline kline = klines.get(1);
						System.out.println(Thread.currentThread().getName() + stopwatch.elapsed());
						return buildKlinePo(kline, symbol, klineType);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.err.println(symbol);
						return null;
					}
				})//
				.collect(Collectors.toList());
		}).get();
		klineDao.saveAll(klinePos);
		// 
		System.out.println(klinePos);
	}

	public KlinePo buildKlinePo(Kline kline, String symbol, KlineType klineType) {
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
		return klinePo;
	}

}
