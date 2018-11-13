package io.github.xinyangpan.crypto4j.chainup.dto.request.replace;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MassReplace {

	private String symbol;
	@JsonProperty("mass_place")
	private List<OrderPiece> massPlace;
	@JsonProperty("mass_cancel")
	private List<Long> massCancel;

}
