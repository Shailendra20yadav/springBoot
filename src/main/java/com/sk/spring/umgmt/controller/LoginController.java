package com.sk.spring.umgmt.controller;

import static com.sk.spring.umgmt.constant.RestApiUriConstant.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(REST)
public class LoginController {
	
	
	@GetMapping(path = LOGIN)
	public String login() {
		return "login successfully";
	}
	
	@PostMapping(path = LOGOUT)
	public String logout() {
		return "logout successfully";
	}
	

}
