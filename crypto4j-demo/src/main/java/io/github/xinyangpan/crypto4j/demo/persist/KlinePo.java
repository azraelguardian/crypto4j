package io.github.xinyangpan.crypto4j.demo.persist;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.github.xinyangpan.crypto4j.demo.core.KlineType;
import lombok.Data;

@Data
@Entity(name = "kline")
public class KlinePo {
	private static final BigDecimal MULTIPLICAND = new BigDecimal("10000");
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String exchange;
	private String symbol;
	@Enumerated(EnumType.STRING)
	private KlineType type;
	private LocalDateTime openTime;
	@Column(precision = 24, scale = 12)
	private BigDecimal open;
	@Column(precision = 24, scale = 12)
	private BigDecimal high;
	@Column(precision = 24, scale = 12)
	private BigDecimal low;
	@Column(precision = 24, scale = 12)
	private BigDecimal close;
	@Column(precision = 24, scale = 12)
	private BigDecimal changePip;
	@Column(precision = 24, scale = 2)
	private BigDecimal changeRatio;
	@Column(precision = 24, scale = 12)
	private BigDecimal volume; // cc1, base
	@Column(precision = 24, scale = 12)
	private BigDecimal quoteAssetVolume; // cc2, quote, profit
	private Integer numberOfTrades;

	public void calculateChange() {
		this.changePip = close.subtract(open);
		this.changeRatio = changePip.multiply(MULTIPLICAND).divide(open, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
	}

}
