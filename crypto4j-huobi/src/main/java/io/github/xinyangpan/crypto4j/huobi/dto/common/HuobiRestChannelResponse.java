package io.github.xinyangpan.crypto4j.huobi.dto.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HuobiRestChannelResponse<T> extends HuobiRestResponse<T> {

	private String ch;
	private Long ts;

}
