package io.github.xinyangpan.crypto4j.chainup.websocket.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.util.ByteBufferBackedInputStream;
import com.google.common.base.Preconditions;

import io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.EventSub;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.params.DepthParam;
import io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.params.Param;
import io.github.xinyangpan.crypto4j.core.websocket.Subscriber;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class ChainupSubscriber extends Subscriber {

	protected String getTextMessage(ByteBuffer payload) throws IOException {
		GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteBufferBackedInputStream(payload));
		String json = IOUtils.toString(gzipInputStream, Charset.forName("utf-8"));
		return json;
	}

	protected void onPing(String jsonMessage) {
		log.debug("{}: Ping - {}", this.getName(), jsonMessage);
	}

	public void depth(String symbol, int step, int askDepth, int bidDepth) {
		// 
		Preconditions.checkNotNull(symbol);
		Preconditions.checkArgument(step >= 0);
		Preconditions.checkArgument(askDepth >= 0);
		Preconditions.checkArgument(bidDepth >= 0);
		// 
		log.info("Subscribing marketDepth. symbol={}, step={}, askDepth={}, bidDepth={}.", symbol, step, askDepth, bidDepth);
		String channel = String.format("market_%s_depth_step%s", symbol, step);
		// 
		DepthParam depthParam = new DepthParam();
		depthParam.setChannel(channel);
		depthParam.setCbId(channel);
		depthParam.setAsks(askDepth);
		depthParam.setBids(bidDepth);
		// 
		EventSub<Param> eventSub = new EventSub<>();
		eventSub.setEvent("sub");
		eventSub.setParams(depthParam);
		this.send(eventSub);
	}

	public void depth(String symbol, int depth) {
		this.depth(symbol, 0, depth, depth);
	}

	public void trade(String symbol) {
		// 
		Preconditions.checkNotNull(symbol);
		// 
		log.info("Subscribing trade. symbol={}.", symbol);
		String channel = String.format("market_%s_trade_ticker", symbol);
		EventSub<Param> eventSub = new EventSub<>();
		eventSub.setEvent("sub");
		eventSub.setParams(new Param(channel, channel));
		this.send(eventSub);
	}

	public void tick(String symbol) {
		// 
		Preconditions.checkNotNull(symbol);
		// 
		log.info("Subscribing ticker. symbol={}.", symbol);
		String channel = String.format("market_%s_ticker", symbol);
		EventSub<Param> eventSub = new EventSub<>();
		eventSub.setEvent("sub");
		eventSub.setParams(new Param(channel, channel));
		this.send(eventSub);
	}

}
