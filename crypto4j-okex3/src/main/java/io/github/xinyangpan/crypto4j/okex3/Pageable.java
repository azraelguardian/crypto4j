package io.github.xinyangpan.crypto4j.okex3;

import lombok.Data;

@Data
public class Pageable {

	private Long from;
	private Long to;
	private Integer limit;

}
