package io.github.xinyangpan.crypto4j.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.xinyangpan.crypto4j.huobi.rest.HuobiRestService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HuobiService {

	@Autowired
	private HuobiRestService huobiRestService;
	//
	private @Getter List<String> symbols;

	@PostConstruct
	public void init() {
		symbols = huobiRestService.symbols().fethData().stream()//
			.filter(symbol -> !symbol.getSymbol().equals("bt1btc") && !symbol.getSymbol().equals("bt2btc"))//
			.map(symbol -> symbol.getSymbol())//
			.collect(Collectors.toList());
		log.info("Total Symbol Size: {}", symbols.size());
	}

}
