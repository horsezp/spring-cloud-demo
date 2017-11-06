package com.leo.eurekaserverhademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
@EnableEurekaServer//这句是重点 不然显示不了界面
@SpringBootApplication
public class EurekaServerHaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerHaDemoApplication.class, args);
	}
}
//启动的时候需要加入 --spring.profiles.active=peer1