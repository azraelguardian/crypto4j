package io.github.xinyangpan.crypto4j.okex3.rest;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Okex3RestProperties extends RestProperties {

	private String passphrase;

}
