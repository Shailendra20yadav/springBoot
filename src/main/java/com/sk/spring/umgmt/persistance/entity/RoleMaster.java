package com.sk.spring.umgmt.persistance.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sk.spring.umgmt.persistance.audit.Audit;
import com.sk.spring.umgmt.persistance.audit.AuditListener;
import com.sk.spring.umgmt.persistance.audit.Auditable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
@Table(name="role_master")
@EntityListeners(AuditListener.class)
public class RoleMaster implements Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long roleid;

	private String roleName;

	private String roleDesc;

	@OneToOne(mappedBy = "roleMaster", fetch = FetchType.LAZY)
	private Authority authority;

	@Embedded
	private Audit audit;

}
