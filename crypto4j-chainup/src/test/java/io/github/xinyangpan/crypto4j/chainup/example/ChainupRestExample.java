package io.github.xinyangpan.crypto4j.chainup.example;

import java.math.BigDecimal;

import com.google.common.collect.Lists;

import io.github.xinyangpan.crypto4j.chainup.dto.enums.OrderType;
import io.github.xinyangpan.crypto4j.chainup.dto.enums.Side;
import io.github.xinyangpan.crypto4j.chainup.dto.enums.TradeSort;
import io.github.xinyangpan.crypto4j.chainup.dto.request.Order;
import io.github.xinyangpan.crypto4j.chainup.dto.request.TradeParam;
import io.github.xinyangpan.crypto4j.chainup.dto.request.replace.MassReplace;
import io.github.xinyangpan.crypto4j.chainup.dto.request.replace.OrderPiece;
import io.github.xinyangpan.crypto4j.chainup.rest.ChainupRestService;
import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;

public class ChainupRestExample {

	private static final ChainupRestService chainupRestService;
	protected static final String BTCUSDT = "btcusdt";
	protected static final String HIEXXFNH = "hiexxfnh";

	static {
		RestProperties restProperties = new RestProperties();
//		restProperties.setRestBaseUrl("http://openapi.xfnh.com/");
		restProperties.setRestBaseUrl("https://openapi.hiex.pro/");
		restProperties.setRestKey(Crypto4jUtils.getSecret("chainup.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("chainup.secret"));
		// 
		chainupRestService = new ChainupRestService(restProperties);
	}

	public static Order createOrder() {
		Order order = new Order();
		order.setSide(Side.BUY);
		order.setSymbol(HIEXXFNH);
		order.setType(OrderType.LIMIT);
		order.setVolume(new BigDecimal("1"));
		order.setPrice(new BigDecimal("0.878"));
		return order;
	}

	public static OrderPiece createOrderPiece() {
		OrderPiece orderPiece = new OrderPiece();
		orderPiece.setSide(Side.BUY);
		orderPiece.setType(OrderType.LIMIT);
		orderPiece.setVolume(new BigDecimal("1"));
		orderPiece.setPrice(new BigDecimal("0.878"));
		return orderPiece;
	}

	public static TradeParam tradeParam() {
		TradeParam tradeParam = new TradeParam();
		tradeParam.setSymbol(HIEXXFNH);
		tradeParam.setPage(1);
		tradeParam.setPageSize(2);
		tradeParam.setSort(TradeSort.ASC);
		tradeParam.setStartDate("2018-11-09 11:57:00");
		tradeParam.setEndDate("2018-11-09 11:58:00");
		return tradeParam;
	}

	public static MassReplace massReplace() {
		MassReplace massReplace = new MassReplace();
		massReplace.setSymbol(HIEXXFNH);
		massReplace.setMassCancel(Lists.newArrayList("26579"));
		massReplace.setMassPlace(Lists.newArrayList(createOrderPiece()));
		return massReplace;
	}

	public static void main(String[] args) throws Exception {
		//		System.out.println(chainupRestService.getAllSymbols());
		//		System.out.println(chainupRestService.getTick(HIEXXFNH));
//		System.out.println(chainupRestService.getAccountInfo());
		System.out.println(chainupRestService.getAllOrder(BTCUSDT));
//				System.out.println(chainupRestService.createOrder(createOrder()));
		//		System.out.println(chainupRestService.cancelOrder(8198, BTCUSDT));
//				System.out.println(chainupRestService.getOrderInfo(25765, HIEXXFNH));
		//		System.out.println(chainupRestService.getAllTrades(tradeParam()));
//		System.out.println(chainupRestService.massReplace(massReplace()));
	}

}
