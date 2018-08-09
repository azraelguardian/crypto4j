package io.github.xinyangpan.crypto4j.demo.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "kline")
public class KlinePo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
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
	private BigDecimal volume;
	private LocalDateTime closeTime;
	@Column(precision = 24, scale = 12)
	private BigDecimal quoteAssetVolume;
	private Integer numberOfTrades;

}
