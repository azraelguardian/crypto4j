package io.github.xinyangpan.crypto4j.chainup.dto.request.replace;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(Include.NON_NULL)
@Data
public class MassReplace {

	private String symbol;
	@JsonProperty("mass_place")
	private List<OrderPiece> massPlace;
	@JsonProperty("mass_cancel")
	private List<String> massCancel;

}
