package io.aetherit.ats.ws.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.aetherit.ats.ws.model.ATSResultSet;
import io.aetherit.ats.ws.model.ATSReturnSet;
import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.live.ATSLiveRoomServer;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.model.type.ATSLiveRoomStatus;
import io.aetherit.ats.ws.service.AuthenticationService;
import io.aetherit.ats.ws.service.LiveRoomService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
public class LiveRoomController {
	
	private static final Logger logger = LoggerFactory.getLogger(LiveRoomController.class);
	
	private LiveRoomService liveRoomService;
	private AuthenticationService authenticationService;
	

    @Autowired
    public LiveRoomController(LiveRoomService liveRoomService, AuthenticationService authenticationService) {
        this.liveRoomService = liveRoomService;
        this.authenticationService = authenticationService;
    }
    
    
    
    @GetMapping("/liveroom")
    public ResponseEntity<Object> getLiveRoomList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd) {
        List<ATSLiveRoomServer> liveRoomList = liveRoomService.getLiveRoomList(langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg("")
										        .success(true)
										        .data(liveRoomList)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PostMapping("/liveroom")
    public ResponseEntity<Void> setLiveRoom(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd, @RequestBody ATSLiveRoom liveRoom) {
    	ATSSimpleUser user = authenticationService.getUser();
    	liveRoom.setUserId(user.getUserId());
//    	liveRoom.setUserId(10000000);
    	liveRoom.setStatusCode(ATSLiveRoomStatus.WAIT);
    	
        liveRoomService.setLiveRoom(liveRoom);
         
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PostMapping("/server-room")
    public ResponseEntity<Void> setServerRoom(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd, @RequestBody ATSServerRoom serverRoom) {
    	
        liveRoomService.setServerRoom(serverRoom);
         
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
	
}