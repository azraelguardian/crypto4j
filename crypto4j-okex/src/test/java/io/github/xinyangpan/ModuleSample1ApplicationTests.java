package io.github.xinyangpan;

import java.time.LocalDateTime;

import org.junit.Test;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.okex.rest.OkexRestService;

public class ModuleSample1ApplicationTests {

	public static OkexRestService okexRestService;
	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://www.okex.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("/home/panxy/okex.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("/home/panxy/okex.secret"));
		//  
		okexRestService = new OkexRestService(restProperties);
	}
	
	@Test
	public void contextLoads() {
		for (int i = 0; i < 6; i++) {
			System.out.println(LocalDateTime.now() + " - " + okexRestService.userinfo());
		}
	}

}
