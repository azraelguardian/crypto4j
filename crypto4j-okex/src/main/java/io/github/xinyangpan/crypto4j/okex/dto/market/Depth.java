package io.github.xinyangpan.crypto4j.okex.dto.market;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class Depth {

	private List<OkexDepthEntry> bids;
	private List<OkexDepthEntry> asks;
	private Long timestamp;
	
	public void setAsks(List<OkexDepthEntry> asks) {
		this.asks = Lists.newArrayList(Lists.reverse(asks));
	}
	
}
