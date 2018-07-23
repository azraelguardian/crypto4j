package io.github.xinyangpan.crypto4j.common.websocket.subscriber;

import static io.github.xinyangpan.crypto4j.exchange.ExchangeUtils.objectMapper;

import java.util.function.Supplier;

import org.springframework.util.Assert;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.Setter;

@Setter
public class BaseDynamicWsSubscriber extends BaseWsSubscriber implements DynamicWsSubscriber {

	protected Supplier<WebSocketSession> sessionSupplier;

	protected void sendTextMessage(Object message) {
		try {
			WebSocketSession webSocketSession = sessionSupplier.get();
			Assert.state(webSocketSession != null, "Session is null.");
			webSocketSession.sendMessage(new TextMessage(objectMapper().writeValueAsString(message)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
