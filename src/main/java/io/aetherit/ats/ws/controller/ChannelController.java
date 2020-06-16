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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.aetherit.ats.ws.model.ATSResultSet;
import io.aetherit.ats.ws.model.ATSReturnSet;
import io.aetherit.ats.ws.model.common.ATSSelection;
import io.aetherit.ats.ws.model.dao.ATSMovieChannelCtg;
import io.aetherit.ats.ws.model.movie.ATSMovieChannel;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.service.ChannelService;

@RestController
public class ChannelController {
	
	private static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	
	private ChannelService channelService;
	

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }
    
    
    
    @GetMapping("/home/defaultchannels")
    public ResponseEntity<Object> getDefaultChannelList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd) {
        List<ATSMovieChannelCtg> defaultChannelList = channelService.getDefaultChannelList(langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg("")
										        .success(true)
										        .data(defaultChannelList)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
    
    @GetMapping("/home/selection/query")
    public ResponseEntity<Object> getSelectionChannelList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd) {
        List<ATSSelection> selectionChannelList = channelService.getSelectionChannelList(langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg("true")
										        .success(true)
										        .data(selectionChannelList)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/home/selection/query2")
    public ResponseEntity<Object> getSelectionChannel2List(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																					 , @RequestParam(value = "moduleId", required=true) int moduleId) {
        List<ATSSelection> selectionChannelList = channelService.getSelectionChannel2List(moduleId,langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg("true")
										        .success(true)
										        .data(selectionChannelList)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/home/channelNoUser")
    public ResponseEntity<Object> getAllChannelList(HttpServletRequest httpRequest,@RequestHeader(value="lang-code") ATSLangCode langCd) {
        List<ATSMovieChannel> allChannelList = channelService.getAllChannelList(langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg("true")
										        .success(true)
										        .data(allChannelList)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
	
}