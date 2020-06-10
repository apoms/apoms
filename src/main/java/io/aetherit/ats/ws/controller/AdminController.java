package io.aetherit.ats.ws.controller;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.aetherit.ats.ws.application.support.FileStorageProperties;
import io.aetherit.ats.ws.model.admin.ATSMovieAttach;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.type.ATSUserStatus;
import io.aetherit.ats.ws.model.type.ATSUserType;
import io.aetherit.ats.ws.service.AdminService;
import io.aetherit.ats.ws.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private AdminService adminService;
	private UserService userService;
	private FileStorageProperties fileStorageProperties;

    @Autowired
    public AdminController(AdminService adminService, 
			    		   UserService userService,
			    		   FileStorageProperties fileStorageProperties) {
        this.adminService = adminService;
        this.userService = userService;
        this.fileStorageProperties = fileStorageProperties;
    }
    
    
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PostMapping("/movie")
    public ResponseEntity<Void> setChannelAttach(HttpServletRequest httpServletRequest, @RequestPart ATSMovieAttach movieAttach
    																				  , @RequestPart MultipartFile[] files) throws Exception {

//    	String domain = fileStorageProperties.getFilesDomain();
//    	String fileName = UUID.randomUUID().toString()+"."+FilenameUtils.getExtension(file.getOriginalFilename());
//    	String filePath = fileStorageProperties.getMoviePath()+"/"+movieAttach.getMovId()+"/"+fileName;
    	
    	/**
    	 * TODO : 동영상 정보 입력 값 세팅해서 보낼 것
    	 */
    	ATSMovieBas movieBas = ATSMovieBas.builder()
    									  .build();
    	
    	adminService.uploadMovieAttachObject(movieBas,files);
        
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
    
    
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PutMapping("/approval/{userId}")
    public ResponseEntity<Void> approvalUserTypeAnchor(HttpServletRequest httpServletRequest, @PathVariable long userId) throws Exception {
    	
    	adminService.approvalUserTypeAnchor(userId);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    
    
    
	
}