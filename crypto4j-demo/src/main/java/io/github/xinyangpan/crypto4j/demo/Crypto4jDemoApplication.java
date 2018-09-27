package io.github.xinyangpan.crypto4j.demo;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.github.xinyangpan.crypto4j.core.RestProperties;
import io.github.xinyangpan.crypto4j.core.util.Crypto4jUtils;
import io.github.xinyangpan.crypto4j.demo.persist.KlinePo;
import io.github.xinyangpan.crypto4j.okex.rest.OkexRestService;

@SpringBootApplication
@EntityScan(basePackageClasses = { KlinePo.class, Jsr310JpaConverters.class })
@EnableJpaRepositories
@EnableTransactionManagement
@EnableScheduling
public class Crypto4jDemoApplication {

	public static OkexRestService okexRestService;
	static {
		RestProperties restProperties = new RestProperties();
		restProperties.setRestBaseUrl("https://104.25.20.25");
		restProperties.setRestKey(Crypto4jUtils.getSecret("/home/panxy/okex.key"));
		restProperties.setRestSecret(Crypto4jUtils.getSecret("/home/panxy/okex.secret"));
		//  
		okexRestService = new OkexRestService(restProperties);
	}
	
	public static void main(String[] args) {
		// 
//		ConfigurableApplicationContext context = SpringApplication.run(Crypto4jDemoApplication.class, args);
//		HuobiWsKlineProcess huobiWsKlineProcess = context.getBean(HuobiWsKlineProcess.class);
//		huobiWsKlineProcess.start();
		int total = 1;
		if (args.length > 0) {
			total = Integer.parseInt(args[0]);
		}
		for (int i = 0; i < total; i++) {
			System.out.println(String.format("%s - %s - %s", i, LocalDateTime.now(), okexRestService.userinfo()));
		}
	}

}
