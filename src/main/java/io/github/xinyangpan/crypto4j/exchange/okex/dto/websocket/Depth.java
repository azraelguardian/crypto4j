package io.github.xinyangpan.crypto4j.exchange.okex.dto.websocket;

import java.util.List;

import lombok.Data;

@Data
public class Depth {

	private List<DepthEntry> bids;
	private List<DepthEntry> asks;
	private Long timestamp;
	
}
