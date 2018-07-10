package io.github.xinyangpan.crypto4j.exchange.huobi.core;

import static io.github.xinyangpan.crypto4j.exchange.huobi.util.HuobiUtils.objectMapper;

import java.util.function.Consumer;

import org.springframework.util.Assert;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiWsSubscriber;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.common.HuobiWsRequest;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.kline.KlineData;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.marketdepth.MarketDepthData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuobiWsSubscriberImpl implements HuobiWsSubscriber {
	private final HuobiWsHandler huobiWsHandler;

	public HuobiWsSubscriberImpl(HuobiWsHandler huobiWsHandler) {
		this.huobiWsHandler = huobiWsHandler;
	}

	@Override
	public void marketDepth(String symbol, String type, Consumer<MarketDepthData> listener) {
		log.info("Subscribing marketDepth. symbol=%s, type=%s.", symbol, type);
		String sub = String.format("market.%s.depth.%s", symbol, type);
		String id = sub;
		this.send(new HuobiWsRequest(id, sub));
	}

	@Override
	public void kline(String symbol, String period, Consumer<KlineData> listener) {
		log.info("Subscribing kline. symbol=%s, type=%s.", symbol, period);
		String sub = String.format("market.%s.kline.%s", symbol, period);
		String id = sub;
		this.send(new HuobiWsRequest(id, sub));
	}

	private void send(Object message) {
		try {
			WebSocketSession webSocketSession = huobiWsHandler.getSession();
			Assert.state(webSocketSession != null, "Session is null.");
			webSocketSession.sendMessage(new TextMessage(objectMapper().writeValueAsString(message)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
