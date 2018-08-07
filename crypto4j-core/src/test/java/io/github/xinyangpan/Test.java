package io.github.xinyangpan;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {

	public static void main(String[] args) {
		double d = 1e6;
		Marker marker = MarkerFactory.getMarker("time");
		log.debug(marker, "d={}", d);
		log.debug(marker, "d={}", d);
		log.debug(marker, "d={}", d);
		log.debug(marker, "d={}", d);
	}

}
