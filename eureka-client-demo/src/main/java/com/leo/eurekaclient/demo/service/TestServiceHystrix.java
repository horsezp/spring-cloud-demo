package com.leo.eurekaclient.demo.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class TestServiceHystrix implements TestService {

	@Override
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "server error")
	public String getData(@PathVariable("name") String name) {
		return name +":out of service from Feign";
	}

}
