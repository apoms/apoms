package io.aetherit.ats.ws.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.ATSUserToken;
import io.aetherit.ats.ws.repository.UserRepository;


@Service
public class AuthenticationService {
	
    private UserAuthenticationProvider authenticationManager;

    @Autowired
    public AuthenticationService(UserAuthenticationProvider authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

	public ATSUserToken getToken(String userId, String rawPassword, HttpSession session) {
        final Authentication request = new UsernamePasswordAuthenticationToken(userId, rawPassword);
        final Authentication result = authenticationManager.authenticate(request);
        
        if ((result != null) && (result.isAuthenticated())) {
            SecurityContextHolder.getContext().setAuthentication(result);
        } else {
            return null;
        }
//        ATSSimpleUser simpleUser = (ATSSimpleUser) result.getDetails();

        return ATSUserToken.builder()
                .token(session.getId())
                .user((ATSSimpleUser) result.getDetails())
                .build();
    }
    
    

    public ATSSimpleUser getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (ATSSimpleUser) authentication.getDetails();
    }

    

}
