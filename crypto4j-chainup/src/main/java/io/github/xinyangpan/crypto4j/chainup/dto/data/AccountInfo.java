package io.github.xinyangpan.crypto4j.chainup.dto.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AccountInfo {

	@JsonProperty("total_asset")
	private String totalAsset;
	@JsonProperty("coin_list")
	private List<Balance> coinList;

}
