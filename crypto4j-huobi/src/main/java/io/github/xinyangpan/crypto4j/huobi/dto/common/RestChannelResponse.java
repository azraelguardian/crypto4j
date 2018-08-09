package io.github.xinyangpan.crypto4j.huobi.dto.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestChannelResponse<T> extends RestResponse<T> {

	private String ch;
	private Long ts;
	private T tick;

	@Override
	public T fethData() {
		T data = super.fethData();
		if (data != null) {
			return data;
		}
		return tick;
	}

}
