package io.aetherit.ats.ws.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.ATSUserSignIn;
import io.aetherit.ats.ws.model.ATSUserToken;
import io.aetherit.ats.ws.service.AuthenticationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/api/v1/authentications")
public class AuthenticationController {
	
	private static final int MAX_INACTIVE_INTERVAL = 21600; // 6 hour
	
    private AuthenticationService authenticationService;
    
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ATSUserToken> getLoginToken(HttpServletRequest httpRequest, HttpSession session, @RequestBody @Valid ATSUserSignIn account) {
    	
    	httpRequest.getSession().setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
        final ATSUserToken token = authenticationService.getToken(account.getUserId(), account.getPassword(), session);
        return new ResponseEntity<ATSUserToken>(token, HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
	@PostMapping("/signout")
    public ResponseEntity logout(HttpServletRequest httpRequest, HttpServletResponse resp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            new SecurityContextLogoutHandler().logout(httpRequest, resp, auth);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @GetMapping("/signcheck")
    public ResponseEntity<ATSSimpleUser> check(HttpServletRequest httpRequest) {
        final ATSSimpleUser user = authenticationService.getUser();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
