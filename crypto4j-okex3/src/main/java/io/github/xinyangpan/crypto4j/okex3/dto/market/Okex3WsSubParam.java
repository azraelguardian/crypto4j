package io.github.xinyangpan.crypto4j.okex3.dto.market;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class Okex3WsSubParam {
	
	@JsonIgnore
	private String business;
	@JsonIgnore
	private String channel;
	@JsonIgnore
	private String filter;
	
	@JsonValue
	public String toString() {
		return String.format("%s/%s:%s", business,channel,filter);
	}
}
