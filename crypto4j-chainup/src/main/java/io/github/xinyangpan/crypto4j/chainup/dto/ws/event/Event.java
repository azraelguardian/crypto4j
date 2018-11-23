package io.github.xinyangpan.crypto4j.chainup.dto.ws.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Event<T> {

	private String channel;
	private Long ts;
	private T tick;

	@JsonProperty("event_rep")
	private String eventRep;
	private String data;
	private String status;

	public boolean isSuccessful() {
		if ("ok".equals(status)) {
			return true;
		}
		return false;
	}

}
