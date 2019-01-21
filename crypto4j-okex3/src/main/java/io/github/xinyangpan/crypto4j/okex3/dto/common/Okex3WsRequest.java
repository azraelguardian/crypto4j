package io.github.xinyangpan.crypto4j.okex3.dto.common;

import java.util.List;

import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import io.github.xinyangpan.crypto4j.okex3.dto.market.Okex3WsSubParam;
import lombok.Data;

@Data
public class Okex3WsRequest<T> {

	private String op;
	private List<T> args;

	public static Okex3WsRequest<Okex3WsSubParam> subscribe(Okex3WsSubParam subParam) {
		if(StringUtils.isEmpty(subParam.getBusiness())) {
			subParam.setBusiness("spot");
		}
		
		Okex3WsRequest<Okex3WsSubParam> wsRequest = new Okex3WsRequest<>();
		wsRequest.setArgs(Lists.newArrayList(subParam));
		wsRequest.setOp("subscribe");
		return wsRequest;
	}
}
