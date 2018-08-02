package io.github.xinyangpan.crypto4j.common.websocket.subscriber;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseWsSubscriber implements WsSubscriber {

	@Override
	public void unhandledMessage(Object obj) {
		log.warn("Unhandled Message: {}", obj, new RuntimeException());
	}

}
