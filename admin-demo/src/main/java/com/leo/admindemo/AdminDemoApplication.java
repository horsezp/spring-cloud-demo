package com.leo.admindemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.config.EnableAdminServer;

@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class AdminDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminDemoApplication.class, args);
	}
}
