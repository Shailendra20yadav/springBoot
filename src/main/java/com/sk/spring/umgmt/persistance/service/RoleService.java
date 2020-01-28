package com.sk.spring.umgmt.persistance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sk.spring.umgmt.persistance.entity.RoleMaster;
import com.sk.spring.umgmt.persistance.repository.RoleMasterRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleMasterRepository roleMasterRepository;
	
	@Transactional(rollbackFor = Throwable.class)
	public long createNewRole(RoleMaster roleMaster) {
		return roleMasterRepository.save(roleMaster).getRoleid();

	}

}
