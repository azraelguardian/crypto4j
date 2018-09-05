package io.github.xinyangpan.crypto4j.exchange.example.okex;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.exchange.example.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.okex.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.okex.dto.market.Depth;
import io.github.xinyangpan.crypto4j.okex.dto.market.OkexDepthEntry;
import io.github.xinyangpan.crypto4j.okex.dto.trade.Order;
import io.github.xinyangpan.crypto4j.okex.dto.trade.QueryOrderResponse;
import io.github.xinyangpan.crypto4j.okex.rest.OkexRestService;

public class OkexRestExample {
	private static final String BTCUSDT = "btc_usdt";
	private static OkexRestService okexRestService;
	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://www.okex.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("okex.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("okex.secret"));
		// 
		okexRestService = new OkexRestService(restProperties);
		
	}

	static void tryPartialFill() throws InterruptedException {
		// 
		Order order = new Order();
		order.setAmount(new BigDecimal("0.08"));
		order.setPrice(new BigDecimal("7800"));
		order.setSymbol(BTCUSDT);
		order.setType(OrderType.buy);
		
		while(true) {
			Depth depth = okexRestService.depth(BTCUSDT);
			OkexDepthEntry entry = depth.getAsks().get(0);
//			BigDecimal amount = entry.getPrice().multiply(entry.getQuantity());
			if (order.getAmount().compareTo(entry.getQuantity()) < 0) {
				System.out.println("continue ...  " + entry);
				Thread.sleep(1000L);
				continue;
			}
			System.out.println(entry);
			order.setPrice(entry.getPrice().stripTrailingZeros());
			QueryOrderResponse orderResponse = okexRestService.placeAndQueryOrder(order);
			System.out.println(orderResponse);
			break;
		}
	}
	
	static void placeOrder() {
		// 
		Order order = new Order();
		order.setAmount(new BigDecimal("0.001"));
		order.setPrice(new BigDecimal("7000"));
		order.setSymbol(BTCUSDT);
		order.setType(OrderType.sell);
		// 
		System.out.println(okexRestService.simulateIocAndQueryOrder(order));
	}
	
	public static void main(String[] args) throws InterruptedException {
//		tryPartialFill();
		placeOrder();
		System.out.println();
	}

}
	