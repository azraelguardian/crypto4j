package io.github.xinyangpan.crypto4j.binance.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseDto {

	@JsonProperty("e")
	protected String eventType;
	@JsonProperty("E")
	protected long eventTime;

	public String getEventTimeText() {
		LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(eventTime), ZoneId.systemDefault());
		return DateTimeFormatter.ISO_DATE_TIME.format(localDateTime);
	}

	@Override
	public String toString() {
		return String.format("AbstractDto [eventType=%s, eventTime=%s]", eventType, eventTime);
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public long getEventTime() {
		return eventTime;
	}

	public void setEventTime(long eventTime) {
		this.eventTime = eventTime;
	}

}
