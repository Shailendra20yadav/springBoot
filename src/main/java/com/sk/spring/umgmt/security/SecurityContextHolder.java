package com.sk.spring.umgmt.security;

import com.sk.spring.umgmt.bean.AuthenticatedUser;

/**
 * 
 * @author shailendra.kum
 *
 *         <p>
 *         Note: inspired by spring LocaleContextHolder
 *         </p>
 */

public enum SecurityContextHolder {

    INSTANCE;

    private ThreadLocal<AuthenticatedUser> securityContext = new SecurityContext();

    private ThreadLocal<AuthenticatedUser> inheritableSecurityContext = new InheritableSecurityContext();

    public void setRealm(AuthenticatedUser realm) {
        setRealm(realm, false);
    }

    public void setRealm(AuthenticatedUser realm, boolean inheritable) {
        if (inheritable) {
            inheritableSecurityContext.set(realm);
            securityContext.remove();
        } else {
            securityContext.set(realm);
            inheritableSecurityContext.remove();
        }
    }

    public AuthenticatedUser getRealm() {
        AuthenticatedUser realm = securityContext.get();

        if (realm == null) {
            realm = inheritableSecurityContext.get();
        }

        return realm;
    }
}

