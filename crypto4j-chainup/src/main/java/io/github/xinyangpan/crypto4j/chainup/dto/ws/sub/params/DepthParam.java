package io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DepthParam extends Param {

	private Integer asks;
	private Integer bids;

}
