package io.aetherit.ats.ws.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.ATSUserSignUp;
import io.aetherit.ats.ws.service.AuthenticationService;
import io.aetherit.ats.ws.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	private UserService userService;
	
	private AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }
	
    /**
     * 사용자 등록
     * @param user
     * @return
     * @throws Exception
     */
	@PostMapping(value = "/signup")
	public ResponseEntity<Object> signup(HttpServletRequest httpServletRequest, @RequestBody @Valid ATSUserSignUp userSignUp) throws Exception{
		return new ResponseEntity<Object>(userService.createNewUser(userSignUp), HttpStatus.OK);
	}
	
	
	/**
	 * 사용자 조회 : 기가입 여부 확인용
	 * @param httpRequest
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/signupcheck")
	public ResponseEntity<Object> getUserCheck(HttpServletRequest httpRequest, @RequestParam(value = "user-id", required=true) String userId) throws Exception{
		ATSUser user = userService.getUser(userId);
		boolean result = false;
		if(user!=null) result = true;
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("result", result);
		return new ResponseEntity<Object>(resultMap, HttpStatus.OK);
	}
	
	
	/**
	 * 사용자 조회 : 단건
	 * @param httpRequest
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
	@GetMapping(value = "/{userId}")
	public ResponseEntity<Object> getUserInfo(HttpServletRequest httpRequest, @PathVariable String userId) throws Exception{
		ATSSimpleUser user = authenticationService.getUser();
		return new ResponseEntity<Object>(userService.getUser(userId), HttpStatus.OK);
	}
    
}
