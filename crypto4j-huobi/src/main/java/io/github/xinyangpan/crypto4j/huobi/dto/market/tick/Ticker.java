package io.github.xinyangpan.crypto4j.huobi.dto.market.tick;

import io.github.xinyangpan.crypto4j.huobi.dto.market.kline.Kline;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Ticker extends Kline {

	private Long version;

}
