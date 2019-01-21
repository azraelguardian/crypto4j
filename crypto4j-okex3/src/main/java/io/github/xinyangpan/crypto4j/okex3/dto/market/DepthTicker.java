package io.github.xinyangpan.crypto4j.okex3.dto.market;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DepthTicker {

	@JsonProperty(value="instrument_id")
	private String instrumentId;
	private String timestamp;
	private String checksum;
	
	private List<Okex3DepthEntry> bids;
	private List<Okex3DepthEntry> asks;
}
