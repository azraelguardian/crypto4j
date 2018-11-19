package io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.params;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Param {
	
	private String channel;
	@JsonProperty("cb_id")
	private String cbId;
	
}
