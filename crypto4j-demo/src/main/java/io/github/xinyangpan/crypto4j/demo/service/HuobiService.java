package io.github.xinyangpan.crypto4j.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;

@Service
public class HuobiService {

	@Autowired
	private HuobiRestService huobiRestService;
	//
	private List<String> symbols;

//	@PostConstruct
	public void init() {
		symbols = huobiRestService.symbols().fethData().stream()//
			.filter(symbol -> !symbol.getSymbol().equals("bt1btc") && !symbol.getSymbol().equals("bt2btc"))//
			.map(symbol -> symbol.getSymbol())//
			.collect(Collectors.toList());
		System.out.println(symbols.size());
	}
	
}
