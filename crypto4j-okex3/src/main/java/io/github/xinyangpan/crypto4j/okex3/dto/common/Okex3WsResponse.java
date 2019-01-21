package io.github.xinyangpan.crypto4j.okex3.dto.common;

import java.util.List;

import lombok.Data;

@Data
public class Okex3WsResponse<T> {
	private String table;
	private List<T> data;
	private String action;
}
