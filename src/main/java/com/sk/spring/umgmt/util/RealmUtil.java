package com.sk.spring.umgmt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sk.spring.umgmt.bean.AuthenticatedUser;
import com.sk.spring.umgmt.security.SecurityContextHolder;

public enum RealmUtil {

    INSTANCE;

    private static final Logger LOG = LoggerFactory.getLogger(RealmUtil.class);

    private SecurityContextHolder securityContext = SecurityContextHolder.INSTANCE;

    public AuthenticatedUser getUser() {
        return securityContext.getRealm();
    }

    public long getLoggedInUserId() {
    	long userId = 0;
        AuthenticatedUser user = securityContext.getRealm();

        if (user == null) {
            LOG.warn("user.info.not.found");
        } else {
            userId = user.getUserid();
        }

        return userId;
    }
    
    public void setUser(AuthenticatedUser user, boolean inheritable) {
    	securityContext.setRealm(user, inheritable);
    }
    
    public void setUser(AuthenticatedUser user) {
    	securityContext.setRealm(user);
    }


}
