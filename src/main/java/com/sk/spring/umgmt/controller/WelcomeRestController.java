package com.sk.spring.umgmt.controller;

import static com.sk.spring.umgmt.constant.RestApiUriConstant.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(REST_API)
public class WelcomeRestController {
	
	@GetMapping(path = "/welcome")
	public String welcome() {
		return "welcome from rest controller";
	}

}
