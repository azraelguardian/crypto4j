package io.github.xinyangpan.crypto4j.exchange.binance.subscription;

import lombok.Data;

@Data
public class BaseStreamSubscription implements StreamSubscription {

	protected String streamName;

}
