package io.github.xinyangpan.crypto4j.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.github.xinyangpan.crypto4j.binance.dto.rest.market.Kline;
import io.github.xinyangpan.crypto4j.binance.dto.rest.market.KlineParam;
import io.github.xinyangpan.crypto4j.binance.rest.BinanceRestService;
import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.demo.core.KlineDao;
import io.github.xinyangpan.crypto4j.demo.core.KlinePo;

@SpringBootApplication
@EntityScan(basePackageClasses = { KlinePo.class, Jsr310JpaConverters.class })
@EnableJpaRepositories
@EnableTransactionManagement
public class Demo {

	public static void main(String[] args) {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://api.binance.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("binance.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("binance.secret"));
		BinanceRestService binanceRestService = new BinanceRestService(restProperties);
		// 
		ConfigurableApplicationContext context = SpringApplication.run(Demo.class, args);
		KlineDao klineDao = context.getBean(KlineDao.class);
		klineDao.deleteAll();
		// 
		KlineParam klineParam = new KlineParam();
		klineParam.setSymbol("BTCUSDT");
		klineParam.setInterval("1m");
		klineParam.setLimit(1000);
		List<Kline> klines = binanceRestService.kline(klineParam);
		List<KlinePo> klinePos = klines.stream().map(Demo::buildKlinePo).collect(Collectors.toList());
		klineDao.saveAll(klinePos);
		System.exit(1);
	}

	public static KlinePo buildKlinePo(Kline kline) {
		if (kline == null) {
			return null;
		}
		KlinePo klinePo = new KlinePo();
		klinePo.setLow(kline.getLow());
		klinePo.setHigh(kline.getHigh());
		klinePo.setCloseTime(kline.getCloseTime());
		klinePo.setClose(kline.getClose());
		klinePo.setOpenTime(kline.getOpenTime());
		klinePo.setVolume(kline.getVolume());
		klinePo.setOpen(kline.getOpen());
		klinePo.setChangePip(klinePo.getClose().subtract(klinePo.getOpen()));
		klinePo.setChangeRatio(klinePo.getChangePip().multiply(new BigDecimal("10000")).divide(klinePo.getOpen(), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
		klinePo.setNumberOfTrades(kline.getNumberOfTrades());
		klinePo.setQuoteAssetVolume(kline.getQuoteAssetVolume());
		return klinePo;
	}
}
