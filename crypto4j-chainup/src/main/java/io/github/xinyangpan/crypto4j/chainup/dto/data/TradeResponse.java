package io.github.xinyangpan.crypto4j.chainup.dto.data;

import java.util.List;

import lombok.Data;

@Data
public class TradeResponse {

	private Integer count;
	private List<TradeResult> resultList;

}
