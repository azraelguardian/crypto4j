package io.github.xinyangpan.crypto4j.chainup.example;

import java.math.BigDecimal;

import io.github.xinyangpan.crypto4j.chainup.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.chainup.dto.enums.Side;
import io.github.xinyangpan.crypto4j.chainup.dto.enums.TradeSort;
import io.github.xinyangpan.crypto4j.chainup.dto.request.Order;
import io.github.xinyangpan.crypto4j.chainup.dto.request.TradeParam;
import io.github.xinyangpan.crypto4j.chainup.rest.ChainupRestService;
import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;

public class ChainupRestExample {

	private static final ChainupRestService chainupRestService;
	protected static final String BTCUSDT = "btcusdt";

	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://dev5openapi.chaindown.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("chainup.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("chainup.secret"));
		// 
		chainupRestService = new ChainupRestService(restProperties);
	}

	public static Order createOrder() throws Exception {
		Order order = new Order();
		order.setSide(Side.BUY);
		order.setSymbol(BTCUSDT);
		order.setType(OrderType.LIMIT);
		order.setVolume(new BigDecimal("0.01"));
		order.setPrice(new BigDecimal("6400"));
		return order;
	}
	
	public static void main(String[] args) throws Exception {
//		System.out.println(chainupRestService.getAllSymbols());
//		System.out.println(chainupRestService.getTick(BTCUSDT));
//		System.out.println(chainupRestService.getAccountInfo());
//		System.out.println(chainupRestService.createOrder(createOrder()));
//		System.out.println(chainupRestService.cancelOrder(8198, BTCUSDT));
//		System.out.println(chainupRestService.getOrderInfo(8196, BTCUSDT));
		TradeParam tradeParam = new TradeParam();
		tradeParam.setSymbol(BTCUSDT);
		tradeParam.setPage(1);
		tradeParam.setPageSize(1);
		tradeParam.setSort(TradeSort.ASC);
		tradeParam.setStartDate("2018-11-09 11:57:00");
		System.out.println(chainupRestService.getAllTrades(tradeParam));
	}

}
