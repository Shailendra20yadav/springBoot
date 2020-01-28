package com.sk.spring.umgmt.bean;

import com.sk.spring.umgmt.persistance.entity.RoleMaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RoleMasterBean {

	private long roleid;

	private String roleName;

	private String roleDesc;

	public RoleMasterBean(RoleMaster master) {
		this.roleid = master.getRoleid();
		this.roleName = master.getRoleName();
		this.roleDesc = master.getRoleDesc();
	}

}
