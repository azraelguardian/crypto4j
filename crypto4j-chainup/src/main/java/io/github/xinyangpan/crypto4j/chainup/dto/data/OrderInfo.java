package io.github.xinyangpan.crypto4j.chainup.dto.data;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.chainup.dto.enums.OrderStatus;
import io.github.xinyangpan.crypto4j.chainup.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.chainup.dto.enums.Side;
import lombok.Data;

@Data
public class OrderInfo {

	private Integer id;
	private Side side;
	private String baseCoin;
	private String countCoin;
	@JsonProperty("side_msg")
	private String sideMsg;
	private OrderType type;
	// 购买数量（多义，复用字段） 
	// 		type=1:表示买卖数量 
	// 		type=2:买则表示总价格，卖表示总个数
	@JsonProperty("volume")
	private BigDecimal volume;

	@JsonProperty("total_price")
	private BigDecimal totalPrice;
	@JsonProperty("avg_price")
	private BigDecimal avgPrice;
	@JsonProperty("price")
	private BigDecimal price;

	@JsonProperty("source")
	private Integer source;
	@JsonProperty("source_msg")
	private String sourceMsg;

	@JsonProperty("status")
	private OrderStatus status;
	@JsonProperty("status_msg")
	private String statusMsg;

	@JsonProperty("deal_volume")
	private BigDecimal dealVolume;
	@JsonProperty("remain_volume")
	private BigDecimal remainVolume;

	@JsonProperty("created_at")
	private Long createdAt;

	private List<Trade> tradeList;

}
