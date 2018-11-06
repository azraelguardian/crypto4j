package io.github.xinyangpan.crypto4j.chainup.dto.request;

import io.github.xinyangpan.crypto4j.chainup.dto.enums.TradeSort;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeParam {

	private String symbol;
	private Integer pageSize;
	private Integer page;
	private TradeSort Sort;

}
