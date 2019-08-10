package com.sk.spring.umgmt.persistance.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the authorities database table.
 * 
 */
@Entity
@Table(name="authorities")
@NamedQuery(name="Authority.findAll", query="SELECT a FROM Authority a")
public class Authority implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="auth_id")
	private long authId;

	private String authority;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="username",referencedColumnName="username")
	private User user;

	public Authority() {
	}

	public long getAuthId() {
		return this.authId;
	}

	public void setAuthId(long authId) {
		this.authId = authId;
	}

	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (authId ^ (authId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authority other = (Authority) obj;
		if (authId != other.authId)
			return false;
		return true;
	}
	
	

}