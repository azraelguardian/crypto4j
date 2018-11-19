package io.github.xinyangpan.crypto4j.chainup.dto.ws.sub;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EventAck {

	@JsonProperty("event_rep")
	private String eventRep;
	private String channel;
	@JsonProperty("cb_id")
	private String cbId;
	private Long ts;
	private String status;

	public boolean isSuccessful() {
		if ("ok".equals(status)) {
			return true;
		}
		return false;
	}

}
