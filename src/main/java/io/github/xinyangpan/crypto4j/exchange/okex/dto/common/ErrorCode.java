package io.github.xinyangpan.crypto4j.exchange.okex.dto.common;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.github.xinyangpan.crypto4j.exchange.okex.OkexUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCode {
	private Integer errorCode;
	private String errorMessage;

	@JsonCreator
	public static ErrorCode create(Integer errorCode) {
		String errorMessage = OkexUtils.getErrorMessage(errorCode);
		return new ErrorCode(errorCode, errorMessage);
	}

}
