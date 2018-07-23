package io.github.xinyangpan.crypto4j.exchange.okex.dto.websocket;

import java.util.List;

import lombok.Data;

@Data
public class Depth {

	private List<OkexDepthEntry> bids;
	private List<OkexDepthEntry> asks;
	private Long timestamp;
	
}
