package io.github.xinyangpan.crypto4j.chainup.dto.data;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Trade {

	private Integer id;
	private BigDecimal volume;
	private String feeCoin;
	private BigDecimal price;
	private BigDecimal fee;
	private Long ctime;
	@JsonProperty("deal_price")
	private BigDecimal dealPrice;
	private String type;

}
