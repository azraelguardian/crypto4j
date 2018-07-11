package io.github.xinyangpan.crypto4j.exchange.binance.dto.depth;

import java.util.List;

import lombok.Data;

@Data
public class Depth {

	private Long lastUpdateId;
	private List<DepthEntry> bids;
	private List<DepthEntry> asks;

}
