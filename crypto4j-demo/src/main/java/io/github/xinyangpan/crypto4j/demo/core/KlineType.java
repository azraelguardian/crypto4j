package io.github.xinyangpan.crypto4j.demo.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public enum KlineType {
	K1M(1, "1m", "1min"), K5M(5, "5m", "5min"), K15M(15, "15m", "15min"), K30M(30, "30m", "30min");

	private final int period;
	private final String binanceCode;
	private final String huobiCode;

	private KlineType(int period, String binanceCode, String huobiCode) {
		this.period = period;
		this.binanceCode = binanceCode;
		this.huobiCode = huobiCode;
	}

	public static List<KlineType> getKlineTypeTypes(LocalDateTime localDateTime) {
		List<KlineType> klineTypes = new ArrayList<>();
		for (KlineType klineType : values()) {
			if (klineType.eval(localDateTime)) {
				klineTypes.add(klineType);
			}
		}
		return klineTypes;
	}

	public boolean eval(LocalDateTime localDateTime) {
		int minute = localDateTime.getMinute();
		int mod = minute % period;
		return mod == 0;
	}

	public int getPeriod() {
		return period;
	}

	public String getBinanceCode() {
		return binanceCode;
	}

	public String getHuobiCode() {
		return huobiCode;
	}

}
