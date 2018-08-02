package io.github.xinyangpan.crypto4j.okex.dto.common;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.github.xinyangpan.crypto4j.okex.OkexUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorCode {
	private Integer code;
	private String message;

	@JsonCreator
	public static ErrorCode create(Integer errorCode) {
		String errorMessage = OkexUtils.getErrorMessage(errorCode);
		return new ErrorCode(errorCode, errorMessage);
	}

}
