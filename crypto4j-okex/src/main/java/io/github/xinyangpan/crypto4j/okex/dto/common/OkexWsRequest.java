package io.github.xinyangpan.crypto4j.okex.dto.common;

import lombok.Data;

@Data
public class OkexWsRequest {

	private String event;
	private String channel;

	public static OkexWsRequest addChannel(String channel) {
		OkexWsRequest instance = new OkexWsRequest();
		instance.channel = channel;
		instance.event = "addChannel";
		return instance;
	}

}
