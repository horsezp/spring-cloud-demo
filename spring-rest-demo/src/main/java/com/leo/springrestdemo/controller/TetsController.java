package com.leo.springrestdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//Controller 的包必须在Application 的包或者子包里面 否则不会被载入
public class TetsController {
	
	@GetMapping("/test")
	public String getData() {
		
		return "tested";
	}

}
