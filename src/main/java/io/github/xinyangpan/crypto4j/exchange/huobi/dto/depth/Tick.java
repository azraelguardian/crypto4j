package io.github.xinyangpan.crypto4j.exchange.huobi.dto.depth;

import java.util.List;

import lombok.Data;

@Data
public class Tick {

	private List<String[]> bids;
	private List<String[]> asks;
	private Long ts;
	private Long version;

}
