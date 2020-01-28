package com.sk.spring.umgmt.persistance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sk.spring.umgmt.persistance.entity.User;
import com.sk.spring.umgmt.persistance.repository.UserDetailsRepository;

@Service
public class UserDetailsAuthService implements UserDetailsService {
	
	private static final Logger LOG =  LoggerFactory.getLogger(UserDetailsAuthService.class);
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	/*
	 * @Autowired private PasswordEncoder passwordEncoder;
	 */

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) {
		User user = userDetailsRepository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User with username = " + username + "does not exists");
		}
		LOG.info("user loaded from UserDetailsAuthService ");
		UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
		List<GrantedAuthority> authorities = null;
		if (!CollectionUtils.isEmpty(user.getAuthorities())) {
			authorities = user.getAuthorities().stream().map(auth -> new SimpleGrantedAuthority(auth.getRoleMaster().getRoleName()))
					.collect(Collectors.toList());
		}else {
			authorities =  new ArrayList<>();
		}
		
		return userBuilder.password(user.getPassword())
				.disabled(!user.isEnabled()).authorities(authorities).build();

	}
	
}
