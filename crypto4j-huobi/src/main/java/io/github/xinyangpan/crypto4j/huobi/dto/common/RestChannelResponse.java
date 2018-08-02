package io.github.xinyangpan.crypto4j.huobi.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestChannelResponse<T> extends RestResponse<T> {

	private String ch;
	private Long ts;
	
	@JsonProperty("tick")
	public void setData(T data) {
		super.setData(data);
	}

}
