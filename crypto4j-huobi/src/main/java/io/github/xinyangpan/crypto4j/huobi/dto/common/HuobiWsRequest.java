package io.github.xinyangpan.crypto4j.huobi.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HuobiWsRequest {

	protected String req;
	protected String id;

}