package com.sk.spring.umgmt.controller;

import static com.sk.spring.umgmt.constant.RestApiUriConstant.REST_ADMIN;
import static com.sk.spring.umgmt.constant.RestApiUriConstant.ROLES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.spring.umgmt.persistance.entity.RoleMaster;
import com.sk.spring.umgmt.persistance.service.RoleService;
import com.sk.spring.umgmt.response.RestResponse;
import com.sk.spring.umgmt.type.ResponseItemType;

@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@PostMapping(value = REST_ADMIN+ROLES)
	public ResponseEntity<RestResponse> createNewRole(RoleMaster roleMaster) {
		long roleId = roleService.createNewRole(roleMaster); 
		return ResponseEntity.ok(new RestResponse(ResponseItemType.RESULT, roleId));
		
	}

}
