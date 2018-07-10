package io.github.xinyangpan.crypto4j.exchange.okex.dto;

import java.util.List;

import lombok.Data;

@Data
public class DepthData {

	private List<String[]> bids;
	private List<String[]> asks;
	private Long timestamp;
	
}
