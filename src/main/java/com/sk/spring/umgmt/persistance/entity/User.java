package com.sk.spring.umgmt.persistance.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sk.spring.umgmt.persistance.audit.Audit;
import com.sk.spring.umgmt.persistance.audit.AuditListener;
import com.sk.spring.umgmt.persistance.audit.Auditable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the users database table.
 * 
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder()

@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@EntityListeners(AuditListener.class)
public class User implements Auditable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userid;

	private boolean enabled;

	private String password;

	private String username;
	
	

	// bi-directional many-to-one association to Authority
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Authority> authorities;

	@Embedded
	private Audit audit;

	public Authority addAuthority(Authority authority) {
		this.getAuthorities().add(authority);
		authority.setUser(this);

		return authority;
	}

	public Authority removeAuthority(Authority authority) {
		getAuthorities().remove(authority);
		authority.setUser(null);

		return authority;
	}

}