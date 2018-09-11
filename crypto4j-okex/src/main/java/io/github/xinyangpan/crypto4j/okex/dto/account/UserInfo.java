package io.github.xinyangpan.crypto4j.okex.dto.account;

import io.github.xinyangpan.crypto4j.okex.dto.common.OkexRestResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends OkexRestResponse {

	private Info info;

}
