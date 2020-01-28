package com.sk.spring.umgmt.persistance.audit;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.sk.spring.umgmt.bean.AuthenticatedUser;
import com.sk.spring.umgmt.util.RealmUtil;

public class AuditListener {
	private static final long ADMIN_USERID = 1L;

	@PrePersist
	public void setCreatedOn(Auditable auditable) {
		Audit audit = auditable.getAudit();

		if (audit == null) {
			audit = new Audit();
			auditable.setAudit(audit);
		}

		audit.setCreatedOn(LocalDateTime.now());
		AuthenticatedUser user = RealmUtil.INSTANCE.getUser();
		audit.setCreatedBy(null != user ? user.getUserid():ADMIN_USERID);
	}

	@PreUpdate
	public void setUpdatedOn(Auditable auditable) {
		Audit audit = auditable.getAudit();

		audit.setUpdatedOn(LocalDateTime.now());
		AuthenticatedUser user = RealmUtil.INSTANCE.getUser();
		audit.setUpdatedBy(null != user ? user.getUserid():ADMIN_USERID);
	}

}
