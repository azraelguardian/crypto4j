package io.github.xinyangpan.crypto4j.exchange.example.binance;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.github.xinyangpan.crypto4j.binance.dto.rest.market.KlineParam;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;

public class Test {
	
	public static void main(String[] args) throws JsonProcessingException {
		KlineParam klineParam = new KlineParam();
		klineParam.setStartTime(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
		System.out.println(Crypto4jUtils.objectMapper().writeValueAsString(klineParam));
	}
	
}
