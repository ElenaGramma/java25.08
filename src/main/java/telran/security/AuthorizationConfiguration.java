package telran.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class AuthorizationConfiguration {
	@Autowired
	CustomWebSecuruty customWebSecuruty;

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http.httpBasic(Customizer.withDefaults()).csrf(csrf->csrf.disable())
		.sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
	
		http.addFilterBefore(new ExpiredPasswordFilter(), BasicAuthenticationFilter.class);
		http.authorizeHttpRequests(authorize->authorize
				.requestMatchers( "/account/register","/account/register/","/cars/*","/model/*").permitAll()
				.requestMatchers(HttpMethod.PUT, "/account/revoke/*","/account/activate/*").hasRole("ADMIN")
				.requestMatchers( "/account/user/*/role/*","/account/activate/*").hasAnyRole("ADMIN","SUPERADMIN")
//				.requestMatchers(HttpMethod.PUT,"/account/user/*").access("@CustomWebSecuruty.checkOwner(#login)")  =>v5
				.requestMatchers(HttpMethod.PUT,"/account/user/*").access((auth,context)->new AuthorizationDecision(customWebSecuruty.checkOwner(context.getVariables().get("login"))))//v 6.2
				.requestMatchers(HttpMethod.GET,"/account/*/{login}").access(new WebExpressionAuthorizationManager("#login == authentication.name or hasRole('ADMIN')"))//v 6.2
				.requestMatchers(HttpMethod.DELETE,"/account/user/{login}").access(new WebExpressionAuthorizationManager("#login == authentication.name or hasRole('ADMIN')"))//v 6.1
				.requestMatchers("/account/login","/account/password","/car/{regNumber}").authenticated()			
			//==================
				
				.requestMatchers(HttpMethod.POST, "/model/add","/car/add").hasRole("MANAGER")
				.requestMatchers(HttpMethod.DELETE, "/car/remove/*","/model/remove/*").hasRole("MANAGER")
				.requestMatchers( "/car/return","/driver/add","/car/rent", "/cars/{modelName}").hasRole("CLERK")
				.requestMatchers(HttpMethod.GET, "/records/*/*").hasRole("TECHNICIAN")
				.requestMatchers(HttpMethod.GET, "drivers/active", "/models/popular", "/models/rofitable/*/*").hasRole("STATIST")
				.requestMatchers(HttpMethod.GET, "/driver/*/cars","/car/*/drivers","/driver/*").hasAnyRole("DRIVER","CLERK")
				
			
				.anyRequest().denyAll());
		
		return http.build();
	}
}
