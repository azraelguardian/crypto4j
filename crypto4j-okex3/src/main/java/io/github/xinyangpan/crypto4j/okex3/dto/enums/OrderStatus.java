package io.github.xinyangpan.crypto4j.okex3.dto.enums;

public enum OrderStatus {
	all, //所有状态 
	open, //未成交 
	part_filled, //部分成交 
	canceling, //撤销中 
	filled, //已成交 
	cancelled, //已撤销 
	ordering,//下单中

}
