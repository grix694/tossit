package com.rix.tossit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages= {"com.rix.tossit"})
@EnableJpaRepositories(basePackages="com.rix.tossit")
@EntityScan(basePackages = { "com.rix.tossit.entity"})
@ComponentScan(basePackages= {"com.rix.tossit"})
public class Application {
	  public static void main(String[] args) throws Exception {
	        SpringApplication.run(Application.class, args);
	    }
}
