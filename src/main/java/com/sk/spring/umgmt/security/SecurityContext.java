package com.sk.spring.umgmt.security;

import org.springframework.util.Assert;

import com.sk.spring.umgmt.bean.AuthenticatedUser;

/**
 * Context holding security realm.
 * 
 * @author shailendra.kum
 * 
 *         <p>
 *         Note: inspired by Spring {LocaleContext}
 *         </p>
 */
public class SecurityContext extends ThreadLocal<AuthenticatedUser> {

    @Override
    public void set(AuthenticatedUser value) {
        Assert.notNull(value, "Only non-null value permitted.");
        super.set(value);
    }

}
