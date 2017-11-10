package com.leo.eurekaclient.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leo.eurekaclient.demo.service.TestService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class MyService {

	@Autowired
	private TestService testService;

	@GetMapping("/client/myservice")
	@HystrixCommand(fallbackMethod = "serviceFail", threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "30"), @HystrixProperty(name = "maxQueueSize", value = "101"),
			@HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
			@HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
			@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
			@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440") }, commandProperties = {
					@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "50") })
	public String getMyService(@RequestParam(value = "name", required = true) String name) {
		return "service:" + testService.getData(name);
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "server error")
	public String serviceFail(String name) {
		return "out of service";
	}
}
