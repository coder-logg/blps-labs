package edu.itmo.blps.configuration;


import edu.itmo.blps.filter.JwtTokenFilter;
import edu.itmo.blps.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsServiceImpl userService;

	@Autowired
	private JwtTokenFilter tokenFilter;

//	@Autowired
//	private JwtAuthenticationFilter filter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.headers().frameOptions().sameOrigin()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/user/login", "/user/signup","/test").permitAll()
				.anyRequest().authenticated()
				.expressionHandler(webExpressionHandler());
		http.addFilterAfter(tokenFilter, UsernamePasswordAuthenticationFilter.class);
//		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//		http.exceptionHandling()
//				.authenticationEntryPoint((request, response, ex) -> {
//					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
//				});
	}

	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userService).passwordEncoder(encoder());
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
		hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER\n" +
				"ROLE_ADMIN > ROLE_CUSTOMER\n" +
				"ROLE_ADMIN > ROLE_COMPANY\n" +
				"ROLE_CUSTOMER > ROLE_USER\n" +
				"ROLE_COMPANY > ROLE_USER");
		return hierarchy;
	}

	private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
		DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());
		return defaultWebSecurityExpressionHandler;
	}



//	@Bean
//	public JwtAuthenticationFilter tokenProcessingFilter() throws Exception {
//		JwtAuthenticationFilter tokenProcessingFilter = new JwtAuthenticationFilter(userService, authenticationManger);
//		tokenProcessingFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
//			SecurityContextHolder.getContext().setAuthentication(authentication);
//			request.getRequestDispatcher(request.getServletPath()).forward(request, response);
//		});
//		tokenProcessingFilter.setAuthenticationFailureHandler((request, response, authenticationException) -> {
//			response.getOutputStream().print(authenticationException.getMessage());
//		});
//		return tokenProcessingFilter;
//	}
}
