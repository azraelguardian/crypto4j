package io.github.xinyangpan.crypto4j.exchange.huobi.dto.marketdepth;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Tick {

	private List<String[]> bids;
	private List<String[]> asks;
	private Long ts;
	private Long version;

}
