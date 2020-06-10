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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.aetherit.ats.ws.model.ATSResultSet;
import io.aetherit.ats.ws.model.ATSReturnSet;
import io.aetherit.ats.ws.model.common.ATSBanner;
import io.aetherit.ats.ws.service.AdvertisingService;

@RestController
public class AdvertisingController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdvertisingController.class);
	
	private AdvertisingService advertisingService;
	

    @Autowired
    public AdvertisingController(AdvertisingService advertisingService) {
        this.advertisingService = advertisingService;
    }
    
    
    
    @GetMapping("/banner/list2")
    public ResponseEntity<Object> getBannerList(HttpServletRequest httpRequest, @RequestParam(value = "langCd", required=true) String langCd) {
        List<ATSBanner> ads = advertisingService.getBannerList(langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg(ads.size()+"")
										        .success(true)
										        .data(ads)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
	
}