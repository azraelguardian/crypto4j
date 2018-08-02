package io.github.xinyangpan.crypto4j.binance.dto.depth;

import java.util.List;

import lombok.Data;

@Data
public class Depth {

	private Long lastUpdateId;
	private List<BinanceDepthEntry> bids;
	private List<BinanceDepthEntry> asks;

}
