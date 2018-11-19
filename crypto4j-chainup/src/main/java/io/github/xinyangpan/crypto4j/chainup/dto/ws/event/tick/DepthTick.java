package io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick;

import java.util.List;

import io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.entry.ChainupDepthEntry;
import lombok.Data;

@Data
public class DepthTick {

	private List<ChainupDepthEntry> bids;
	private List<ChainupDepthEntry> asks;

}
