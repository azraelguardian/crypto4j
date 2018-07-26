package io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.account;

import io.github.xinyangpan.crypto4j.exchange.okex.dto.rest.common.RestResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends RestResponse {

	private Info info;

}
