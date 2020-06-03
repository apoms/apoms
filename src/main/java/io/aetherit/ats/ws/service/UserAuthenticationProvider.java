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
        final String userId = (String) request.getPrincipal();
        final String password = (String) request.getCredentials();
        
        final ATSUser user = userService.getUser(userId);
        if(user == null) {
            throw new UsernameNotFoundException("User not found : " + userId);
        }
        
        String userPassword = user.getPassword();
        
        if ((password != null) && (password.length() > 0) && (passwordEncoder.matches(password, userPassword))) {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getType().name()));

            result = new UsernamePasswordAuthenticationToken(userId, password, authorities);
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
        		.phoneNo(user.getPhoneNo())
        		.userName(user.getUserName())
        		.regDt(user.getRegDt())
        		.modDt(user.getModDt())
        		.build();
    }
}
