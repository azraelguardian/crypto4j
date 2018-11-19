package io.github.xinyangpan.crypto4j.chainup.dto.request.replace;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.xinyangpan.crypto4j.chainup.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.chainup.dto.enums.Side;
import lombok.Data;

@Data
public class OrderPiece {
	private Side side;
	private OrderType type;
	private BigDecimal volume;
	private BigDecimal price;
	@JsonIgnore
	private boolean useExchangeCoin;

	@JsonProperty("fee_is_user_exchange_coin")
	public int getFeeIsUserExchangeCoin() {
		if (useExchangeCoin) {
			return 1;
		} else {
			return 0;
		}
	}

}
