package io.aetherit.ats.ws.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.ATSUserSignUp;
import io.aetherit.ats.ws.model.common.ATSFollower;
import io.aetherit.ats.ws.model.dao.ATSUserRel;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.service.AuthenticationService;
import io.aetherit.ats.ws.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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
	 * user check
	 * @param httpRequest
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/signupcheck")
	public ResponseEntity<Object> getUserCheck(HttpServletRequest httpRequest, @RequestParam(value = "cntryCode", required=true) String cntryCode
																			 , @RequestParam(value = "phoneNo", required=true) String phoneNo) throws Exception{
		ATSUser user = userService.getUserByPhoneNo(cntryCode+phoneNo);
		boolean result = false;
		if(user!=null) result = true;
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("result", result);
		return new ResponseEntity<Object>(resultMap, HttpStatus.OK);
	}
	
	
	/**
	 * user detail
	 * @param httpRequest
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
	@GetMapping(value = "/user")
	public ResponseEntity<Object> getUserInfo(HttpServletRequest httpRequest, @RequestParam(value = "userId", required=false, defaultValue="0") long userId) throws Exception{
		ATSSimpleUser user = authenticationService.getUser();
		
		System.out.println(user.getUserId());
		return new ResponseEntity<Object>(userService.getUser(user.getUserId()), HttpStatus.OK);
	}
	
	
	
	@ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
	@PatchMapping(value = "/anchor")
	public ResponseEntity<Object> setUserTypeAnchor(HttpServletRequest httpRequest) throws Exception{
		ATSSimpleUser user = authenticationService.getUser();
		return new ResponseEntity<Object>(userService.setUserTypeAnchor(user.getUserId()), HttpStatus.OK);
	}
    
	
	@ApiOperation(value = "Calling Anchor's Followers, TYPE : FAN, FOLLOWER")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @GetMapping("/followers")
    public ResponseEntity<Object> getFollowerList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    		 																	, @RequestParam(value = "type", required=false, defaultValue="") String type) {
    	ATSSimpleUser user = authenticationService.getUser();
    	List<ATSFollower> followerList = userService.getFollowerList(user.getUserId());
    	List<ATSFollower> resultList = new ArrayList<ATSFollower>();
    	
    	if(type.equalsIgnoreCase("")) {
    		resultList = followerList;
    	}else{
    		for(ATSFollower follower:followerList) {
    			if(type.equalsIgnoreCase(follower.getRelType().name()))	resultList.add(follower);
    		}
    	}
         
        return new ResponseEntity<Object>(resultList, HttpStatus.OK);
    }
	
	
	@ApiOperation(value = "Calling User's Following, TYPE : FAN, FOLLOWER")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @GetMapping("/followings")
    public ResponseEntity<Object> getFollowingList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    		 																	 , @RequestParam(value = "type", required=false, defaultValue="") String type) {
    	ATSSimpleUser user = authenticationService.getUser();
    	List<ATSFollower> followingList = userService.getFollowingList(user.getUserId());
    	List<ATSFollower> resultList = new ArrayList<ATSFollower>();
    	
    	if(type.equalsIgnoreCase("")) {
    		resultList = followingList;
    	}else{
    		for(ATSFollower following:followingList) {
    			if(type.equalsIgnoreCase(following.getRelType().name()))	resultList.add(following);
    		}
    	}
         
        return new ResponseEntity<Object>(resultList, HttpStatus.OK);
    }
	
	
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PostMapping("/followings")
    public ResponseEntity<Object> setFollowing(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    		 																 , @RequestBody ATSUserRel userRel) {
    	if(userRel.getRelId()==0){
    		userRel.setRelId(authenticationService.getUser().getUserId());
    	}
		userService.setFollowing(userRel); 
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @DeleteMapping("/followings")
    public ResponseEntity<Void> deleteFollowing(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    		 																	, @RequestParam(value = "follow-idx", required=true) int followIdx) {
		userService.deleteFollowing(followIdx);         
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
