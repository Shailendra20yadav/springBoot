package com.sk.spring.umgmt.persistance.audit;

import java.io.Serializable;

public interface Auditable extends Serializable {
	
	Audit getAudit();
	 
    void setAudit(Audit audit);

}
