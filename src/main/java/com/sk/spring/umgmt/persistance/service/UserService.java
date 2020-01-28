package com.sk.spring.umgmt.persistance.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sk.spring.umgmt.bean.AuthenticatedUser;
import com.sk.spring.umgmt.bean.AuthorityBean;
import com.sk.spring.umgmt.bean.RoleMasterBean;
import com.sk.spring.umgmt.persistance.entity.Authority;
import com.sk.spring.umgmt.persistance.entity.RoleMaster;
import com.sk.spring.umgmt.persistance.entity.User;
import com.sk.spring.umgmt.persistance.mapper.DtoToEntityMapper;
import com.sk.spring.umgmt.persistance.repository.RoleMasterRepository;
import com.sk.spring.umgmt.persistance.repository.UserDetailsRepository;

@Service
public class UserService {
	
private static final Logger LOG =  LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Transactional(readOnly=true)
	public AuthenticatedUser getAuthenticatedUserByUsername(String username) {
		AuthenticatedUser authenticatedUser = new AuthenticatedUser();
		User user = userDetailsRepository.findByUsername(username);
		if (user == null) {
			LOG.error(String.format("User with username  %S does not exists", username));
			throw new UsernameNotFoundException("User with username = " + username + "does not exists");
		}
		authenticatedUser.setUserid(user.getUserid());
		authenticatedUser.setUserName(user.getUsername());
		List<AuthorityBean> authorityBeanList = Collections.emptyList();
		if (null != user.getAuthorities()) {
			authorityBeanList = user.getAuthorities().stream()
					.map(auth -> new AuthorityBean(auth.getAuthId(), new RoleMasterBean(auth.getRoleMaster())))
					.collect(Collectors.toList());
		}

		authenticatedUser.setAuthorities(authorityBeanList);
		return authenticatedUser;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public long registerNewUser(AuthenticatedUser authenticatedUser) {
		DtoToEntityMapper<AuthenticatedUser, User> mapper = authUser -> User.builder().userid(authUser.getUserid())
				.username(authUser.getUserName()).password(passwordEncoder.encode(authUser.getPassword())).enabled(true)
				.authorities(authUser.getAuthorities().stream().map(authBean -> {
					Authority authority = new Authority();
					RoleMaster master = roleMasterRepository.findById(authBean.getRoleMasterBean().getRoleid())
							.orElse(null);
					authority.setRoleMaster(master);
					return authority;
				}).collect(Collectors.toList())).build();
		User user = mapper.mapToEntity(mapper, authenticatedUser);
		user.getAuthorities().forEach(auth-> auth.setUser(user));
		return userDetailsRepository.save(user).getUserid();
	}
	

}
