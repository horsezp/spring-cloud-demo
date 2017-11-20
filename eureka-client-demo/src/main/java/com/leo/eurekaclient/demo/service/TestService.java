package com.leo.eurekaclient.demo.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "spring-rest-demo",fallback=TestServiceHystrix.class)
//只需要定义接口 Feign 本身集成了 Hystrix 所以可以在这里配置Hystrix 的处理类

public interface TestService {
	
	//这里定义的是请求路径
	@GetMapping("/test/{name}")
	public String getData(@PathVariable("name") String name) ;

}
