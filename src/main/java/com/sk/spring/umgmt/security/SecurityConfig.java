package com.sk.spring.umgmt.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("customBasicAuthenticationEntryPoint")
    private BasicAuthenticationEntryPoint basicAuthenticationEntryPoint;
	
	/*
	 * @Autowired
	 * can not autowired bean here as it is being created in same file 
	private PasswordEncoder passwordEncoder;
	*/
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	@Qualifier("customLogoutSuccessHandler")
	private LogoutSuccessHandler logoutSuccesHandler;
	
	// Authentication : User --> Roles
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { PasswordEncoder encoder =
	 * PasswordEncoderFactories.createDelegatingPasswordEncoder();
	 * auth.inMemoryAuthentication() .passwordEncoder(encoder)
	 * .withUser("user1").password("{noop}secret1").roles("USER").and().withUser(
	 * "admin1").password("{noop}secret1") .roles("USER", "ADMIN"); }
	 */
	
	/*
	@Autowired
	public void configureJDBC(AuthenticationManagerBuilder auth)throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery("select username, password, enabled"
            + " from users where username=?")
        .authoritiesByUsernameQuery("select username, authority "
            + "from authorities where username=?")
        .passwordEncoder(passwordEncoder());
		
	}*/
	
	@Autowired
	public void configureUserDetailsService(AuthenticationManagerBuilder auth)throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.httpBasic().authenticationEntryPoint(basicAuthenticationEntryPoint).and().authorizeRequests()
				.antMatchers("/rest/welcome/**").hasRole("USER").antMatchers("/rest/welcome/**")
				.hasAnyAuthority("ROLE_USER").antMatchers("/rest/**").hasRole("ADMIN").antMatchers("/rest/**")
				.hasAnyAuthority("ROLE_ADMIN").and().csrf().disable().formLogin().disable().headers().frameOptions()
				.disable();*/
		
		http.httpBasic().authenticationEntryPoint(basicAuthenticationEntryPoint).and().authorizeRequests()
		.antMatchers("/rest/login").permitAll()
		.antMatchers("/rest/api/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
		.antMatchers("rest/admin/**").hasAnyAuthority("ROLE_ADMIN")
		.and().logout().logoutUrl("/rest/logout")
		.clearAuthentication(true).deleteCookies("jsessionid","auth_user").logoutSuccessHandler(logoutSuccesHandler)
		.and().csrf().disable().formLogin().disable().headers().frameOptions()
		.disable();
		

		/*
		 * http //HTTP Basic authentication .httpBasic() .and() .authorizeRequests()
		 * .antMatchers(HttpMethod.GET, "/books/**").hasRole("USER")
		 * .antMatchers(HttpMethod.POST, "/books").hasRole("ADMIN")
		 * .antMatchers(HttpMethod.PUT, "/books/**").hasRole("ADMIN")
		 * .antMatchers(HttpMethod.PATCH, "/books/**").hasRole("ADMIN")
		 * .antMatchers(HttpMethod.DELETE, "/books/**").hasRole("ADMIN") .and()
		 * .csrf().disable() .formLogin().disable();
		 */
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
