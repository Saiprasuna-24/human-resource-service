package com.cts.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.cts.filter.JwtFilterRequest;
import com.cts.service.MyUserDetailService;

@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter
{

	@Autowired
	MyUserDetailService user;
	@Autowired
	JwtFilterRequest req;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		//to provide access for the user
		auth.userDetailsService(user);
	}
	//to skip encoding of password
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	  @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.csrf().disable()
	            .authorizeRequests()
	                .antMatchers("/api/authenticate",
	                		"/v3/api-docs",
	                		"/v3/api-docs/**",
                            "/configuration/ui",
                            "/swagger-resources/**",
                            "/configuration/security",
                            "/swagger-ui.html",
                            "/webjars/**",
                            "/swagger-ui/**").permitAll()
	                .anyRequest().authenticated()
	                .and()
	            .sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	            .addFilterBefore(req, UsernamePasswordAuthenticationFilter.class)
	            .cors().configurationSource(corsConfigurationSource());
	    }

	    @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
	        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	@Bean
	public AuthenticationManager authenticationManagerBean()throws Exception
	{
		return super.authenticationManager();
	}
}
