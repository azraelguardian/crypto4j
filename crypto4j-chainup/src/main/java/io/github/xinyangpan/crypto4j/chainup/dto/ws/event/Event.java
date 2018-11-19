package io.github.xinyangpan.crypto4j.chainup.dto.ws.event;

import lombok.Data;

@Data
public class Event<T> {

	private String channel;
	private Long ts;
	private T t;

}
