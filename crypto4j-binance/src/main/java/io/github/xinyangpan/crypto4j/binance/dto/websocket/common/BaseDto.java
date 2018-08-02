package io.github.xinyangpan.crypto4j.binance.dto.websocket.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BaseDto {

	@JsonProperty("e")
	protected String eventType;
	@JsonProperty("E")
	protected long eventTime;

	public String getEventTimeText() {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(eventTime), ZoneId.systemDefault());
		return DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
	}

}
