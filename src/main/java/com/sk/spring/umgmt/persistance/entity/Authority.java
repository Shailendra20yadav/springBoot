package com.sk.spring.umgmt.persistance.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sk.spring.umgmt.persistance.audit.Audit;
import com.sk.spring.umgmt.persistance.audit.AuditListener;
import com.sk.spring.umgmt.persistance.audit.Auditable;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the authorities database table.
 * 
 */
@Data
@NoArgsConstructor

@Entity
@Table(name="authorities")
@NamedQuery(name="Authority.findAll", query="SELECT a FROM Authority a")
@EntityListeners(AuditListener.class)
public class Authority implements Auditable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "auth_id")
	private long authId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "roleid", referencedColumnName = "roleid")
	private RoleMaster roleMaster;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", referencedColumnName = "userid",updatable = true, insertable = true)
	private User user;
	
	@Embedded
	private Audit audit;

}