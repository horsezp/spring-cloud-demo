package com.leo.configserverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerDemoApplication.class, args);
	}
}

//http://localhost:8888/config-repo/config-client/
//可以在浏览器就看到具体的内容
