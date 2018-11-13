package io.github.xinyangpan.crypto4j.chainup.dto.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MassReplaceResult {

	@JsonProperty("mass_place")
	private List<MassOrderResult> massPlace;
	@JsonProperty("mass_cancel")
	private List<MassOrderResult> massCancel;
	
}
