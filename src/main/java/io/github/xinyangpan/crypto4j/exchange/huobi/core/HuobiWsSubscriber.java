package io.github.xinyangpan.crypto4j.exchange.huobi.core;

import static io.github.xinyangpan.crypto4j.exchange.huobi.util.HuobiUtils.objectMapper;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.util.Assert;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import io.github.xinyangpan.crypto4j.exchange.huobi.HuobiWsEndpoint;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.common.HuobiWsRequest;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.marketdepth.MarketDepthData;
import io.github.xinyangpan.crypto4j.exchange.huobi.dto.tradedetail.TradeDetailData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HuobiWsSubscriber implements HuobiWsEndpoint {
	private final HuobiWsHandler huobiWsHandler;

	public HuobiWsSubscriber(HuobiWsHandler huobiWsHandler) {
		this.huobiWsHandler = huobiWsHandler;
	}

	@Override
	public void marketDepth(String symbol, String type, Consumer<MarketDepthData> listener) {
		log.info("Subscribing symbol=%s, type=%s.", symbol, type);
		String sub = String.format("market.%s.depth.%s", symbol, type);
		String id = sub;
		this.send(new HuobiWsRequest(id, sub));
	}

	@Override
	public void tradeDetail(String symbol, Consumer<TradeDetailData> listener) {
		// TODO Auto-generated method stub

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
