package io.aetherit.ats.ws.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.aetherit.ats.ws.model.ATSResultSet;
import io.aetherit.ats.ws.model.ATSReturnSet;
import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.admin.ATSMovieAttach;
import io.aetherit.ats.ws.model.dao.ATSLiveGiftBas;
import io.aetherit.ats.ws.model.dao.ATSMovieChannelCtg;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.model.type.ATSUserStatus;
import io.aetherit.ats.ws.model.type.ATSUserType;
import io.aetherit.ats.ws.service.AdminService;
import io.aetherit.ats.ws.service.AuthenticationService;
import io.aetherit.ats.ws.service.ChannelService;
import io.aetherit.ats.ws.service.LiveRoomService;
import io.aetherit.ats.ws.service.UserService;
import io.aetherit.ats.ws.util.CommonUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private AdminService adminService;
	private UserService userService;
	private LiveRoomService liveRoomService;
	private ChannelService channelService;
	private AuthenticationService authenticationService;
	private CommonUtil commonUtil;

    @Autowired
    public AdminController(AdminService adminService, 
			    		   UserService userService,
			    		   LiveRoomService liveRoomService,
			    		   ChannelService channelService,
			    		   AuthenticationService authenticationService,
			    		   CommonUtil commonUtil) {
        this.adminService = adminService;
        this.userService = userService;
        this.liveRoomService = liveRoomService;
        this.channelService = channelService;
        this.authenticationService = authenticationService;
        this.commonUtil = commonUtil;
    }
    

    @GetMapping("/defaultchannels")
    public ResponseEntity<Object> getDefaultChannelList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd) {
        List<ATSMovieChannelCtg> defaultChannelList = channelService.getDefaultChannelList(langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg(defaultChannelList.size()+"")
										        .success(true)
										        .data(defaultChannelList)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
    /**
     * sub module List
     * @param httpRequest
     * @param langCd
     * @param moduleId
     * @return
     */
    @GetMapping("/subModulechannels")
    public ResponseEntity<Object> getSubChannelList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																			  , @RequestParam(value = "moduleId", required=false, defaultValue="0") int moduleId) {
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("moduleId", moduleId);
    	
        List<ATSMovieChannelCtg> getSubChannelList = channelService.getSubChannelList(map);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
													        .code(0)
													        .enumCode("SUCCESS")
													        .msg(getSubChannelList.size()+"")
													        .success(true)
													        .data(getSubChannelList)
													        .build())
									      .build(), HttpStatus.OK);
    }
    
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PostMapping("/movie")
    public ResponseEntity<Void> setMovieWithAttach(HttpServletRequest httpServletRequest, @RequestPart ATSMovieAttach movieAttach
    																				  	, @RequestPart MultipartFile[] files
    																				  	, @RequestPart MultipartFile[] coverChnFiles
    																				  	, @RequestPart MultipartFile[] coverEngFiles
    																				  	, @RequestPart MultipartFile[] coverJpnFiles) throws Exception {
    	
//    public ResponseEntity<Void> setMovieWithAttach(HttpServletRequest httpServletRequest, @RequestPart ATSMovieAttach movieAttach
//    			, @RequestPart MultipartFile files
//    			, @RequestPart MultipartFile coverChnFiles
//    			, @RequestPart MultipartFile coverEngFiles
//    			, @RequestPart MultipartFile coverJpnFiles
//    			) throws Exception {
    	
    	ATSSimpleUser user = authenticationService.getUser();
    	adminService.setMovieWithAttach(user,movieAttach,files,coverChnFiles,coverEngFiles,coverJpnFiles);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    
    /**
     * user list : by type
     * @param httpServletRequest
     * @param userType
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @GetMapping("/users")
    public ResponseEntity<Object> getUserTypeList(HttpServletRequest httpServletRequest, @RequestParam(value = "userType", required=false, defaultValue="") ATSUserType type
    																				   , @RequestParam(value = "statusCode", required=false, defaultValue="") ATSUserStatus statusCode) throws Exception {
        
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("type", type);
    	map.put("statusCode", statusCode);
        return new ResponseEntity<Object>(userService.getUsers(map), HttpStatus.OK);
    }
    
    
    /**
     * Anchor approval
     * @param httpServletRequest
     * @param userId
     * @return
     * @throws Exception
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PutMapping("/approval/{userId}")
    public ResponseEntity<Void> approvalUserTypeAnchor(HttpServletRequest httpServletRequest, @PathVariable long userId) throws Exception {
    	
    	adminService.approvalUserTypeAnchor(userId);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PutMapping("/gifts")
    public ResponseEntity<Object> getLiveGiftList(HttpServletRequest httpServletRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																			   , @RequestParam(value = "paging", required=false, defaultValue="no") String paging
																				   , @RequestParam(value = "pageNo", required=false, defaultValue="1") int pageNo
																				   , @RequestParam(value = "pageSize", required=false, defaultValue="30") int pageSize) throws Exception {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("langCd", langCd);
    	map.put("type", "");
    	
    	if(commonUtil.checkPaging(paging)) {
    		map.put("pageSize", pageSize);
			map.put("pageNo", ((pageNo)-1)*pageSize);
    			
			List<ATSLiveGiftBas> giftList = liveRoomService.getGiftList(map);
			int totalCount = liveRoomService.getGiftTotalCount(map);
			
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("resultList",giftList);
			resultMap.put("totalCount",totalCount);
			
			return new ResponseEntity<Object>(resultMap, HttpStatus.OK);
    	}else {
    		map.put("rowsPerPage", 0);
    		map.put("pageNo", 0);
    		
    		List<ATSLiveGiftBas> giftList = liveRoomService.getGiftList(map);
			return new ResponseEntity<Object>(giftList, HttpStatus.OK);
    	}
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @GetMapping("/gifts/{idx}")
    public ResponseEntity<Object> getLiveGift(HttpServletRequest httpRequest, @PathVariable(value="idx", required=true) int idx) throws Exception{
    	ATSLiveGiftBas gift = liveRoomService.getGift(idx);
    	
    	return new ResponseEntity<Object>(gift,HttpStatus.OK);
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PostMapping("/gifts")
    public ResponseEntity<Object> setLiveGift(HttpServletRequest httpRequest, @RequestBody ATSLiveGiftBas liveGift) throws Exception{
    	adminService.setLiveGift(liveGift);
    	return new ResponseEntity<Object>(adminService.setLiveGift(liveGift),HttpStatus.OK);
    }
    
    
	
}