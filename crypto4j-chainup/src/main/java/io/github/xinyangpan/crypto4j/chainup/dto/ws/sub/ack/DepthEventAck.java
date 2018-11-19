package io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.ack;

import io.github.xinyangpan.crypto4j.chainup.dto.ws.sub.EventAck;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DepthEventAck extends EventAck {

	private Integer asks;
	private Integer bids;

}
