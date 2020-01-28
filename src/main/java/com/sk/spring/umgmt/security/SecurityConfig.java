package com.sk.spring.umgmt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.sk.spring.umgmt.security.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	@Qualifier("customBasicAuthenticationEntryPoint")
	private BasicAuthenticationEntryPoint basicAuthenticationEntryPoint;

	/*
	 * @Autowired can not autowired bean here as it is being created in same file
	 * private PasswordEncoder passwordEncoder;
	 */

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	@Qualifier("customLogoutSuccessHandler")
	private LogoutSuccessHandler logoutSuccesHandler;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

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
	 * @Autowired public void configureJDBC(AuthenticationManagerBuilder auth)throws
	 * Exception {
	 * 
	 * auth.jdbcAuthentication().dataSource(dataSource)
	 * .usersByUsernameQuery("select username, password, enabled" +
	 * " from users where username=?")
	 * .authoritiesByUsernameQuery("select username, authority " +
	 * "from authorities where username=?") .passwordEncoder(passwordEncoder());
	 * 
	 * }
	 */

	@Autowired
	public void configureUserDetailsService(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * 
	 * http.httpBasic().authenticationEntryPoint(basicAuthenticationEntryPoint).and(
	 * ).authorizeRequests() .antMatchers("/rest/authenticate").permitAll()
	 * .antMatchers("/rest/api/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
	 * .antMatchers("rest/admin/**").hasAnyAuthority("ROLE_ADMIN")
	 * .and().logout().logoutUrl("/rest/logout")
	 * .clearAuthentication(true).deleteCookies("jsessionid","auth_user").
	 * logoutSuccessHandler(logoutSuccesHandler)
	 * .and().csrf().disable().formLogin().disable().headers().frameOptions()
	 * .disable();
	 * 
	 * 
	 * 
	 * }
	 */

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/rest/authenticate","/rest/signup").permitAll()
				.antMatchers("/rest/api/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
				 .antMatchers("/rest/admin/**").hasAnyAuthority("ROLE_ADMIN")
				// all other requests need to be authenticated
				.anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(basicAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().logout().logoutUrl("/rest/logout")
				 .clearAuthentication(true).deleteCookies("jsessionid","auth_user").
				 logoutSuccessHandler(logoutSuccesHandler);
		// Add a filter to validate the tokens with every request
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
