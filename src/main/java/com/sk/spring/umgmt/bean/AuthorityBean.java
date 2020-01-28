package com.sk.spring.umgmt.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthorityBean {

	private long authId;
	private RoleMasterBean roleMasterBean;

}
