package io.github.xinyangpan.crypto4j.okex.dto.account;

import io.github.xinyangpan.crypto4j.okex.dto.common.RestResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends RestResponse {

	private Info info;

}
