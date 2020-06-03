package io.aetherit.ats.ws.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;



@Configuration
@EnableWebSecurity
@EnableJdbcHttpSession
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private AuthenticationProvider authenticationProvider;

    @Autowired
    public WebSecurityConfiguration(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)

                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // To Do : 빠른 시일에 설정해야 함.
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();

//                .antMatchers("/", "/error", "/home", "/signin", "/signup", "/findpassword", "/mypage", "/intro/**", "/certification/**", "/channel/**", "/broadcast/**", "/group/**", "/board/**", "/quiz/**", "/ticket/**", "/terms/**", "/guide/**", "/admin/**",
//                                            "/api/v1/informations/**",
//                                            "/api/v1/admin/signin",
//                                            "/api/v1/users/signup",
//                							"/api/v1/users/signupcheck", 
//                							"/api/v1/users/password", 
//                							"/api/v1/authentications/**", 
//                							"/api/v1/files/app/**",
//                							"/api/v1/certification/**",
//                							"/api/v1/barcodes/qr",
//                							"/swagger-ui**",
//                							"/static/**",
//                                            "/images/**",
//                                            "/lib/**",
//                							"/ie",
//                							"/manifest.json").permitAll()
//                .antMatchers(HttpMethod.OPTIONS, "/api/v1/admin/**").hasAuthority("ADMIN")
//                .antMatchers(HttpMethod.OPTIONS, "/api/v1/channels/**", 
//                								 "/api/v1/groups/**", 
//                								 "/api/v1/payments/**", 
//                								 "/api/v1/users/**", 
//                								 "/api/v1/boards/**",
//                								 "/api/v1/barcodes/**",
//                								 "/api/v1/quiz/**",
//						 						 "/api/v1/files/**").authenticated()
//                .antMatchers("/api/v1/**").authenticated()
                ;
        
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new HeaderHttpSessionIdResolver("X-Auth-Token");
    }
}

