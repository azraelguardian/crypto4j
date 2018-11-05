package io.github.xinyangpan.crypto4j.chainup.example;

import io.github.xinyangpan.crypto4j.chainup.rest.ChainupRestService;
import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;

public class ChainupRestExample {

	private static final ChainupRestService chainupRestService;
	private static final String BTCUSDT = "btcusdt";

	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://dev5openapi.chaindown.com");
		restProperties.setRestKey(Crypto4jUtils.getSecret("chainup.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("chainup.secret"));
		// 
		chainupRestService = new ChainupRestService(restProperties);
	}

	public static void main(String[] args) throws Exception {
		System.out.println(chainupRestService.getAllSymbols());
		System.out.println(chainupRestService.getTick(BTCUSDT));
	}

}
