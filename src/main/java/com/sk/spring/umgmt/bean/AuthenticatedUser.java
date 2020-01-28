package com.sk.spring.umgmt.bean;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AuthenticatedUser {

	private long userid;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private List<AuthorityBean> authorities;

}
