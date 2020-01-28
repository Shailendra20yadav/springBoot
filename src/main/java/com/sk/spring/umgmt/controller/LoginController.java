package com.sk.spring.umgmt.controller;

import static com.sk.spring.umgmt.constant.RestApiUriConstant.LOGOUT;
import static com.sk.spring.umgmt.constant.RestApiUriConstant.REST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.spring.umgmt.bean.AuthenticatedUser;
import com.sk.spring.umgmt.persistance.service.UserService;
import com.sk.spring.umgmt.security.jwt.JwtRequest;
import com.sk.spring.umgmt.security.jwt.JwtResponse;
import com.sk.spring.umgmt.security.jwt.JwtTokenUtil;

@RestController
@RequestMapping(REST)
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	

	@PostMapping(path = LOGOUT)
	public String logout() {
		return "logout successfully";
	}

	@PostMapping(value = "/authenticate")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@PostMapping(value = "/signup")
	public ResponseEntity<JwtResponse> signupUser(@RequestBody AuthenticatedUser authenticatedUser)
			throws Exception {
		userService.registerNewUser(authenticatedUser);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticatedUser.getUserName());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
