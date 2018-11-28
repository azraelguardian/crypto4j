package io.github.xinyangpan.crypto4j.okex3.dto.account;

import io.github.xinyangpan.crypto4j.okex3.dto.common.OkexRestResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserInfo extends OkexRestResponse {

	private Info info;

}
