package io.github.xinyangpan.crypto4j.exchange.okex.impl;

import static io.github.xinyangpan.crypto4j.exchange.ExchangeUtils.objectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.util.Assert;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.exchange.okex.OkexWsSubscriber;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.DepthData;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.OkexWsRequest;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.OkexWsResponse;
import io.github.xinyangpan.crypto4j.exchange.okex.dto.TickerData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OkexWsSubscriberImpl implements OkexWsSubscriber {
	private final OkexWsHandler okexWsHandler;

	public OkexWsSubscriberImpl(OkexWsHandler okexWsHandler) {
		this.okexWsHandler = okexWsHandler;
	}

	@Override
	public void depth(String symbol, int depth, Consumer<OkexWsResponse<DepthData>> listener) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkArgument(depth >= 0);
		Preconditions.checkNotNull(listener);
		// 
		log.info("Subscribing marketDepth. symbol={}, type={}.", symbol, depth);
		String channel = String.format("ok_sub_spot_%s_depth_%s", symbol, depth);
		okexWsHandler.getDepthListenerMap().put(channel, listener);
		this.send(OkexWsRequest.addChannel(channel));
	}

	@Override
	public void ticker(String symbol, Consumer<OkexWsResponse<TickerData>> listener) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkNotNull(listener);
		// 
		log.info("Subscribing ticker. symbol={}, type={}.", symbol);
		String channel = this.getChannel(symbol);
		okexWsHandler.getTickerListenerMap().put(channel, listener);
		this.send(OkexWsRequest.addChannel(channel));
	}

	@Override
	public void tickers(Consumer<OkexWsResponse<TickerData>> listener, String... symbols) {
		// 
		Preconditions.checkNotNull(symbols);
		Preconditions.checkArgument(symbols.length > 0);
		// 
		List<OkexWsRequest> requests = Arrays.stream(symbols)//
			.map(this::getChannel)// symbol to channel
			.map(channel -> {
				okexWsHandler.getTickerListenerMap().put(channel, listener);
				return channel;
			})// put map: channel -> listener
			.map(OkexWsRequest::addChannel)// channel -> OkexWsRequest
			.collect(Collectors.toList());
		this.send(requests);
	}

	private String getChannel(String symbol) {
		return String.format("ok_sub_spot_%s_ticker", symbol);
	}

	private void send(Object message) {
		try {
			WebSocketSession webSocketSession = okexWsHandler.getSession();
			Assert.state(webSocketSession != null, "Session is null.");
			webSocketSession.sendMessage(new TextMessage(objectMapper().writeValueAsString(message)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
