package io.aetherit.ats.ws.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

import io.aetherit.ats.ws.application.support.FileStorageProperties;
import io.aetherit.ats.ws.exception.CanNotFoundTypeException;
import io.aetherit.ats.ws.exception.FileStorageException;
import io.aetherit.ats.ws.model.ATSResultSet;
import io.aetherit.ats.ws.model.ATSReturnSet;
import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.dao.ATSLiveGiftBas;
import io.aetherit.ats.ws.model.dao.ATSLiveGiftRel;
import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSLiveRoomUserHst;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.live.ATSLiveRoomCurrent;
import io.aetherit.ats.ws.model.live.ATSLiveRoomPatch;
import io.aetherit.ats.ws.model.live.ATSLiveRoomServer;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.model.type.ATSPointType;
import io.aetherit.ats.ws.model.type.ATSUserType;
import io.aetherit.ats.ws.service.AuthenticationService;
import io.aetherit.ats.ws.service.LiveRoomService;
import io.aetherit.ats.ws.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/api/v1/liveroom")
public class LiveRoomController {
	
	private static final Logger logger = LoggerFactory.getLogger(LiveRoomController.class);
	
	private static final long thumbnailSizeLimit = 1048576;
	
	private LiveRoomService liveRoomService;
	private UserService userService;
	private AuthenticationService authenticationService;
	private FileStorageProperties fileStorageProperties;
	

    @Autowired
    public LiveRoomController(LiveRoomService liveRoomService, 
    						  UserService userService,
    						  AuthenticationService authenticationService,
    						  FileStorageProperties fileStorageProperties
    						 ) {
        this.liveRoomService = liveRoomService;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.fileStorageProperties = fileStorageProperties;
    }
    
    
    
    @GetMapping("")
    public ResponseEntity<Object> getLiveRoomList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd) {
        List<ATSLiveRoomCurrent> liveRoomList = liveRoomService.getLiveRoomList(langCd);
         
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
    
    @GetMapping("/ranking")
    public ResponseEntity<Object> getLiveRoomRankingList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd) {
        List<ATSLiveRoomCurrent> liveRoomRankingList = liveRoomService.getLiveRoomRankingList(langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg("")
										        .success(true)
										        .data(liveRoomRankingList)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/room/{roomId}")
    public ResponseEntity<Object> getLiveRoom(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd, @PathVariable(value="roomId", required=true) long roomId) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("roomId", roomId);
    	
        ATSLiveRoomServer liveRoom = liveRoomService.getLiveRoom(map);
        
        List<ATSLiveRoomServer> liveRoomList = new ArrayList<ATSLiveRoomServer>();
        liveRoomList.add(liveRoom);
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
    @PatchMapping("")
    public ResponseEntity<Void> setLiveRoom(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd, @RequestBody ATSLiveRoomPatch liveRoom) {
    	ATSSimpleUser user = authenticationService.getUser();
    	
        liveRoomService.patchLiveRoom(liveRoom);
         
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PostMapping("/server")
    public ResponseEntity<Void> setServerRoom(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd, @RequestBody ATSServerRoom serverRoom) {
    	
        liveRoomService.setServerRoom(serverRoom);
         
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    /**
     * 사용자 프로파일 업로드
     * @param file
     * @return
     */
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PostMapping("/thumbnail")
    public ResponseEntity<Void> uploadFile(HttpServletRequest httpServletRequest, @RequestPart("file") MultipartFile file) throws Exception {
    	
    	
    	if(file.getSize() > thumbnailSizeLimit) {
    		throw new FileStorageException("The file size cannot exceed 1 megabyte.");
    	}
    	
    	ATSSimpleUser user = authenticationService.getUser();
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("userId", user.getUserId());
    	ATSLiveRoomServer liveRoomServer = liveRoomService.getLiveRoom(map);
/**
 * 이미지 사이즈 체크하는 로직    	
    	try
        {
    		long result = file.getSize();
    		
    		File convFile = new File(file.getOriginalFilename());
    		convFile.createNewFile();
    		FileOutputStream fos = new FileOutputStream(convFile);
    		fos.write(file.getBytes());
    		fos.close();
    		
			BufferedImage bi = ImageIO.read(convFile);
			System.out.println("width : " + bi.getWidth() + " height : " + bi.getHeight() );
        } catch( Exception e ) {
        	e.printStackTrace();
        	System.out.println("이미지 파일 아님");
        }
*/       
    	
    	String fileName = UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(file.getOriginalFilename());
    	String thrnnailPath = fileStorageProperties.getThumbnailUrl();
    	String thumbnailUrl = thrnnailPath + "/" + liveRoomServer.getLiveRoom().getRoomId() + "/" + fileName;
    	
    	ATSLiveRoom liveRoom = ATSLiveRoom.builder()
    								.roomId(liveRoomServer.getLiveRoom().getRoomId())
    								.thumbnailUrl(thumbnailUrl)
    								.build();
    	
    	
    	liveRoomService.uploadThumbnailObject(liveRoom,file,fileName,thumbnailUrl);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    /**
     * Live room Access log 
     * @param httpRequest
     * @param langCd
     * @param accessRoomHistory
     * @return
     */
    @PutMapping("/view")
    public ResponseEntity<Void> setLiveRoomAccess(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd, @RequestBody ATSLiveRoomUserHst accessRoomHistory) {
    	if(!Objects.isNull(accessRoomHistory.getUserId())) {
    		accessRoomHistory.setTypeCode(userService.getUser(accessRoomHistory.getUserId()).getType());
    	}else {
    		accessRoomHistory.setTypeCode(ATSUserType.ANONYMOUS);
    	}
        liveRoomService.setLiveRoomAccess(accessRoomHistory);
         
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    @GetMapping("/gifts")
    public ResponseEntity<Object> getGiftList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																		, @RequestParam(value = "type", required=false, defaultValue="") String type) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("langCd", langCd);
    	map.put("type", type);
    	
        List<ATSLiveGiftBas> giftList = liveRoomService.getGiftList(map);
         
//        return new ResponseEntity<Object>(ATSReturnSet.builder()
//									      .data(ATSResultSet.builder()
//										        .code(0)
//										        .enumCode("SUCCESS")
//										        .msg(giftList.size()+"")
//										        .success(true)
//										        .data(giftList)
//										        .build())
//									      .build(), HttpStatus.OK);
        
        return new ResponseEntity<Object>(giftList,HttpStatus.OK);
    }
    
    
    @GetMapping("/gifts/{idx}")
    public ResponseEntity<Object> getGift(HttpServletRequest httpRequest, @PathVariable(value="idx", required=true) int idx) {
    	ATSLiveGiftBas gift = liveRoomService.getGift(idx);
    	
//        return new ResponseEntity<Object>(ATSReturnSet.builder()
//									      .data(ATSResultSet.builder()
//										        .code(0)
//										        .enumCode("SUCCESS")
//										        .msg(giftList.size()+"")
//										        .success(true)
//										        .data(gift)
//										        .build())
//									      .build(), HttpStatus.OK);
    	
    	return new ResponseEntity<Object>(gift,HttpStatus.OK);
    }
    
    
    @PostMapping("/donate")
    public ResponseEntity<Void> setDonate(HttpServletRequest httpRequest, @RequestBody ATSLiveGiftRel liveGiftRel) {
        liveRoomService.setDonate(liveGiftRel);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}