package io.github.xinyangpan.crypto4j.okex3.dto.market;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.github.xinyangpan.crypto4j.core.dto.market.DepthEntry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Okex3DepthEntry extends DepthEntry {
	private BigDecimal orderNumbers;

	@JsonCreator
	public static Okex3DepthEntry create(String[] data) {
		Okex3DepthEntry depthEntry = new Okex3DepthEntry();
		depthEntry.setPrice(new BigDecimal(data[0]));
		depthEntry.setQuantity(new BigDecimal(data[1]));
		depthEntry.setOrderNumbers(new BigDecimal(data[2]));
		
		return depthEntry;
	}
}
