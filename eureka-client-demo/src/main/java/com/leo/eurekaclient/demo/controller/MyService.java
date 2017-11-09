package com.leo.eurekaclient.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class MyService {

	
	@GetMapping("/client/myservice")
	@HystrixCommand(fallbackMethod = "serviceFail")
	public String getMyService(String name) {

		//TODO 熔断一般用在调用其他服务的地方 如果其他服务不可以用 应该快速的失败
		return "service:" + name;
	}

	
	public String serviceFail(String name) {

		return "out of service";
	}
}
