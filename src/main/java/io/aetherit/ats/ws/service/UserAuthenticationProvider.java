package io.aetherit.ats.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.ATSUserSignIn;
import io.aetherit.ats.ws.model.type.ATSUserStatus;
import io.aetherit.ats.ws.model.type.ATSUserType;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication request) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, request, "Only UsernamePasswordAuthenticationToken is supported");

        UsernamePasswordAuthenticationToken result = null;
        final String phoneNo = (String) request.getPrincipal();
        final String password = (String) request.getCredentials();
        
        final ATSUser user = userService.getUserByPhoneNo(phoneNo);
        if(user == null) {
            throw new UsernameNotFoundException("User not found : " + phoneNo);
        }
        
        String userPassword = user.getPassword();
        
        if ((password != null) && (password.length() > 0) && (passwordEncoder.matches(password, userPassword))) {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getType().name()));

            result = new UsernamePasswordAuthenticationToken(phoneNo, password, authorities);
            result.setDetails(getSimpleUser(user));
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
        return result;
    }
    
    public Authentication anoymousAuthenticate(Authentication request,String passwd) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, request, "Only UsernamePasswordAuthenticationToken is supported");

        UsernamePasswordAuthenticationToken result = null;
        final String phoneNo = (String) request.getPrincipal();
        final String password = (String) request.getCredentials();
        
        final ATSUser user = ATSUser.builder()
        							.cntryCode("+86")
        							.phoneNo("00000000000")
        							.nickName("anoymous")
        							.type(ATSUserType.ANOYMOUS)
        							.userId(0)
        							.statusCode(ATSUserStatus.NORMAL)
        							.password(passwordEncoder.encode(passwd))
        							.build();
        
        String userPassword = user.getPassword();
        
        if ((password != null) && (password.length() > 0) && (passwordEncoder.matches(password, userPassword))) {
	        final List<GrantedAuthority> authorities = new ArrayList<>();
	        authorities.add(new SimpleGrantedAuthority(user.getType().name()));
	
	        result = new UsernamePasswordAuthenticationToken(phoneNo, password, authorities);
	        result.setDetails(getSimpleUser(user));
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }

    private ATSSimpleUser getSimpleUser(ATSUser user) {
        return ATSSimpleUser.builder()
        		.userId(user.getUserId())
        		.phoneNo(user.getCntryCode()+user.getPhoneNo())
        		.nickName(user.getNickName())
        		.type(user.getType())
        		.regDt(user.getRegDt())
        		.modDt(user.getModDt())
        		.build();
    }
}
