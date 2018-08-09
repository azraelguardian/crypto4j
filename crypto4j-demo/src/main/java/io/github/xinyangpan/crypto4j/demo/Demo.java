package io.github.xinyangpan.crypto4j.demo;

import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.github.xinyangpan.crypto4j.demo.persist.KlinePo;
import io.github.xinyangpan.crypto4j.demo.service.HuobiKlineService;

@SpringBootApplication
@EntityScan(basePackageClasses = { KlinePo.class, Jsr310JpaConverters.class })
@EnableJpaRepositories
@EnableTransactionManagement
@EnableScheduling
public class Demo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// 
		ConfigurableApplicationContext context = SpringApplication.run(Demo.class, args);
		HuobiKlineService huobiKlineService = context.getBean(HuobiKlineService.class);
		huobiKlineService.everyMinute();
	}

}
