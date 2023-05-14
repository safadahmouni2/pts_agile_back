package com.thinktank.pts.agileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing
public class AgileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgileServiceApplication.class, args);
	}

}
