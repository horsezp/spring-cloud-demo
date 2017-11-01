package com.leo.springrestdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TetsController {
	
	@GetMapping("/test")
	public String getData() {
		
		return "tested";
	}

}
