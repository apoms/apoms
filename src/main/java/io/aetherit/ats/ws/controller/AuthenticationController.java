package io.aetherit.ats.ws.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.ATSUserSignIn;
import io.aetherit.ats.ws.model.ATSUserToken;
import io.aetherit.ats.ws.model.ATSVerify;
import io.aetherit.ats.ws.service.AuthenticationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/authentications")
public class AuthenticationController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	private static final int MAX_INACTIVE_INTERVAL = 60 * 60 * 48; 						// 48 Hour
	
    private AuthenticationService authenticationService;
    
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/anonymous")
    public ResponseEntity<ATSUserToken> getAnonymousToken(HttpServletRequest httpRequest, HttpSession session) {
    	
    	httpRequest.getSession().setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
        final ATSUserToken token = authenticationService.getAnonymousToken(session);
        return new ResponseEntity<ATSUserToken>(token, HttpStatus.OK);
    }
    
    @PostMapping("/signin")
    public ResponseEntity<ATSUserToken> getLoginToken(HttpServletRequest httpRequest, HttpSession session, @RequestBody @Valid ATSUserSignIn account) {
    	
    	httpRequest.getSession().setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
        final ATSUserToken token = authenticationService.getToken(account, session);
        return new ResponseEntity<ATSUserToken>(token, HttpStatus.OK);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
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
    
    
    @ApiOperation(value = "Calling Verify Code, TYPE : signup, password")
    @PostMapping("/verifycode/{type}")
    public ResponseEntity<Object> requestVerifyCodePhone(@PathVariable("type") String type, @RequestBody(required=false) ATSUserSignIn user) throws Exception{
//		boolean result = authenticationService.requestVerifyCodePhoneNo(user);
//		String result = authenticationService.requestVerifyCodePhoneNo(user);
		
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		
		String phoneNo = null; 
		if(authentication.getPrincipal().toString() != null) {
			phoneNo = authentication.getPrincipal().toString();	
		}
		
		
//		boolean result = false;
		String result = "";
		
		if(user != null) {
			phoneNo = user.getCntryCode()+user.getName();
		}
			
		logger.debug("PATH ============ TYPE : {} ", type);		
		logger.debug("PHONE NO ============ TYPE : {} ", phoneNo);		
		logger.debug("PATH ============ authentication : {} ", authentication);		
		//SIGNUP 
		if(type.equals("signup")) {
			result = authenticationService.requestVerifyCodePhoneNo(user);
		//PASSOWRD ( login-password ) 
		}else if(type.equals("password")){
			result = authenticationService.verifyForPassword(user);
		}
		
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("verifycode", result);
		
    	return new ResponseEntity<Object>(map, HttpStatus.OK);
    }

}
