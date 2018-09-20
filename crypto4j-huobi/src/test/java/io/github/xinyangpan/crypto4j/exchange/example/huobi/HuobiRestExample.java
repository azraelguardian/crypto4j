package io.github.xinyangpan.crypto4j.exchange.example.huobi;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.exchange.example.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.huobi.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.huobi.dto.market.depth.Depth;
import io.github.xinyangpan.crypto4j.huobi.dto.market.depth.HuobiDepthEntry;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.Order;
import io.github.xinyangpan.crypto4j.huobi.dto.trade.OrderDetail;
import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;

public class HuobiRestExample {

	private static final HuobiRestService huobiRestService;

	private static final String BTCUSDT = "btcusdt";

	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://api.huobi.pro");
		restProperties.setRestKey(Crypto4jUtils.getSecret("huobi.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("huobi.secret"));
		// 
		huobiRestService = new HuobiRestService(restProperties);
	}

	static void tryPartialFill() throws InterruptedException {
		Order order = new Order();
		order.setAccountId(4275858L);
		order.setAmount(new BigDecimal("0.01"));
		order.setPrice(new BigDecimal("7800"));
		order.setSymbol(BTCUSDT);
		order.setType(OrderType.BUY_IOC);
		while (true) {
			Depth depth = huobiRestService.depth(BTCUSDT).fethData();
			HuobiDepthEntry entry = depth.getAsks().get(0);
			//			BigDecimal amount = entry.getPrice().multiply(entry.getQuantity());
			if (order.getAmount().compareTo(entry.getQuantity()) < 0) {
				System.out.println("continue ...  " + entry);
				//				Thread.sleep(1000L);
				//				continue;
			}
			//			System.out.println(entry);
			//			order.setPrice(entry.getPrice().stripTrailingZeros());
			OrderDetail orderDetail = huobiRestService.placeAndQueryDetails(order);
			System.out.println(orderDetail);
			System.out.println(orderDetail.getOrderResult().getState());
			break;
		}
	}

	static void placeOrder() throws InterruptedException {
		Order order = new Order();
		order.setAccountId(4275858L);
		order.setAmount(new BigDecimal("0.1"));
		order.setAmount(new BigDecimal("700"));
		//		order.setPrice(new BigDecimal("7800"));
		order.setSymbol(BTCUSDT);
		order.setType(OrderType.BUY_MARKET);
		OrderDetail orderDetail = huobiRestService.placeAndQueryDetails(order);
		System.out.println(orderDetail);
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(huobiRestService.tickers());
	}
	
}
