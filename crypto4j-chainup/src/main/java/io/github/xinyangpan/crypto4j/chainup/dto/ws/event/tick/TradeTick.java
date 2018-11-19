package io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick;

import java.util.List;

import io.github.xinyangpan.crypto4j.chainup.dto.ws.event.tick.entry.TradeDataEntry;
import lombok.Data;

@Data
public class TradeTick {

	private Long id;
	private Long ts;
	private List<TradeDataEntry> data;

}
