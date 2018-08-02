package io.github.xinyangpan.crypto4j.exchange.binance.dto.rest.market;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import lombok.Data;

@Data
public class ServerTime {

	private long serverTime;

	public LocalDateTime getServerDateTime() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(serverTime), ZoneId.systemDefault());
	}

	@Override
	public String toString() {
		return String.format("ServerTime [serverTime=%s, getServerDateTime()=%s]", serverTime, getServerDateTime());
	}

}
