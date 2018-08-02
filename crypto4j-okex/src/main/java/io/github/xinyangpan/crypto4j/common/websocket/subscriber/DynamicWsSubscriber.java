package io.github.xinyangpan.crypto4j.common.websocket.subscriber;

import java.util.function.Supplier;

import org.springframework.web.socket.WebSocketSession;

public interface DynamicWsSubscriber extends WsSubscriber {

	void setSessionSupplier(Supplier<WebSocketSession> sessionSupplier);

}
