package io.github.xinyangpan.crypto4j.binance.dto.rest.market;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;

//    1499040000000,      // Open time
//    "0.01634790",       // Open
//    "0.80000000",       // High
//    "0.01575800",       // Low
//    "0.01577100",       // Close
//    "148976.11427815",  // Volume
//    1499644799999,      // Close time
//    "2434.19055334",    // Quote asset volume
//    308,                // Number of trades
//    "1756.87402397",    // Taker buy base asset volume
//    "28.46694368",      // Taker buy quote asset volume
//    "17928899.62484339" // Ignore.
@Data
public class Kline {

	private LocalDateTime openTime;
	private BigDecimal open;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal close;
	private BigDecimal volume;
	private LocalDateTime closeTime;
	private BigDecimal quoteAssetVolume;
	private Integer numberOfTrades;
	private BigDecimal takerBuyBaseAssetVolume;
	private BigDecimal takerBuyQuoteAssetVolume;
	private String ignore;

	@JsonCreator
	public static Kline create(Object[] data) {
		Kline kline = new Kline();
		kline.openTime = LocalDateTime.ofInstant(Instant.ofEpochMilli((long) data[0]), ZoneId.systemDefault());
		kline.open = new BigDecimal((String) data[1]);
		kline.high = new BigDecimal((String) data[2]);
		kline.low = new BigDecimal((String) data[3]);
		kline.close = new BigDecimal((String) data[4]);
		kline.volume = new BigDecimal((String) data[5]);
		kline.closeTime = LocalDateTime.ofInstant(Instant.ofEpochMilli((long) data[6]), ZoneId.systemDefault());
		kline.quoteAssetVolume = new BigDecimal((String) data[7]);
		kline.numberOfTrades = (Integer) data[8];
		kline.takerBuyBaseAssetVolume = new BigDecimal((String) data[9]);
		kline.takerBuyQuoteAssetVolume = new BigDecimal((String) data[10]);
		kline.ignore = (String) data[11];
		return kline;
	}

}
