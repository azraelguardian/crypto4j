package io.github.xinyangpan.crypto4j.huobi.dto.market.depth;

import java.util.List;

import lombok.Data;

@Data
public class Depth {

	private List<HuobiDepthEntry> bids;
	private List<HuobiDepthEntry> asks;
	private Long ts;
	private Long version;

}
