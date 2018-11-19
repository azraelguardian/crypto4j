package io.github.xinyangpan.crypto4j.chainup.dto.ws.sub;

import io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.params.Param;
import lombok.Data;

@Data
public class EventSub<T extends Param> {

	private String event;
	private T params;

}
