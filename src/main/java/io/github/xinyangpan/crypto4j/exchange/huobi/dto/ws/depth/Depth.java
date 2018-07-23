package io.github.xinyangpan.crypto4j.exchange.huobi.dto.ws.depth;

import java.util.List;

import lombok.Data;

@Data
public class Depth {

	private List<DepthEntry> bids;
	private List<DepthEntry> asks;
	private Long ts;
	private Long version;

}
