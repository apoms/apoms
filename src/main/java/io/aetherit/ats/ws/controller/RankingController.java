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

import io.aetherit.ats.ws.model.ATSPageResultSet;
import io.aetherit.ats.ws.model.ATSPageReturnSet;
import io.aetherit.ats.ws.model.ATSResultSet;
import io.aetherit.ats.ws.model.ATSReturnSet;
import io.aetherit.ats.ws.model.actor.ATSActorRanking;
import io.aetherit.ats.ws.model.movie.ATSMovieSearchHot;
import io.aetherit.ats.ws.model.movie.ATSRanking;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.service.RankingService;

@RestController
public class RankingController {
	
	private static final Logger logger = LoggerFactory.getLogger(RankingController.class);
	
	private RankingService rankingService;
	

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }
    
    
    @GetMapping("/ranking/viewmov")
    public ResponseEntity<Object> getRankingViewMovieList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																					, @RequestParam(value = "pageNo", required=false, defaultValue="1") int pageNo
    																					, @RequestParam(value = "pageSize", required=false, defaultValue="30") int pageSize
    																					, @RequestParam(value = "type", required=false, defaultValue="1") int type) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("pageNo", ((pageNo)-1)*pageSize);
    	map.put("pageSize", pageSize);
    	map.put("type", type);
    	
        List<ATSRanking> rankingViewMovieList = rankingService.getRankingViewMovieList(map,langCd);
        int rankingTotalCount = rankingService.getRankingViewMovieTotalCount(map);
        int pageCount = rankingTotalCount/pageSize;
        int divideRest = rankingTotalCount%pageSize;
        if(divideRest!=0)	pageCount++;
        
        return new ResponseEntity<Object>(ATSPageReturnSet.builder()
													      .data(ATSPageResultSet.builder()
																		        .code(0)
																		        .enumCode("SUCCESS")
																		        .msg("30")
																		        .success(true)
																		        .count(rankingTotalCount)
																		        .pageCount(pageCount)
																		        .pageNo(pageNo)
																		        .pageSize(pageSize)
																		        .data(rankingViewMovieList)
																		        .build())
													      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/ranking/newmov")
    public ResponseEntity<Object> getRankingNewMovieList(HttpServletRequest httpRequest , @RequestHeader(value="lang-code") ATSLangCode langCd
																			    		, @RequestParam(value = "pageNo", required=false, defaultValue="1") int pageNo
																						, @RequestParam(value = "pageSize", required=false, defaultValue="30") int pageSize
																						, @RequestParam(value = "type", required=false, defaultValue="1") int type) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("pageNo", ((pageNo)-1)*pageSize);
    	map.put("pageSize", pageSize);
    	map.put("type", type);
    	
        List<ATSRanking> rankingNewMovieList = rankingService.getRankingNewMovieList(map,langCd);
        int rankingTotalCount = rankingService.getRankingNewMovieTotalCount(map);
        int pageCount = rankingTotalCount/pageSize;
        int divideRest = rankingTotalCount%pageSize;
        if(divideRest!=0)	pageCount++;
        
        return new ResponseEntity<Object>(ATSPageReturnSet.builder()
													      .data(ATSPageResultSet.builder()
																		        .code(0)
																		        .enumCode("SUCCESS")
																		        .msg("30")
																		        .success(true)
																		        .count(rankingTotalCount)
																		        .pageCount(pageCount)
																		        .pageNo(pageNo)
																		        .pageSize(pageSize)
																		        .data(rankingNewMovieList)
																		        .build())
													      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/ranking/lusir")
    public ResponseEntity<Object> getRankingLusirMovieList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
																			    		 , @RequestParam(value = "pageNo", required=false, defaultValue="1") int pageNo
																						 , @RequestParam(value = "pageSize", required=false, defaultValue="30") int pageSize
																						 , @RequestParam(value = "type", required=false, defaultValue="1") int type) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("pageNo", ((pageNo)-1)*pageSize);
    	map.put("pageSize", pageSize);
    	map.put("type", type);
    	
        List<ATSRanking> rankingLusirMovieList = rankingService.getRankingLusirMovieList(map,langCd);
        int rankingTotalCount = rankingService.getRankingLusirMovieTotalCount(map);
        int pageCount = rankingTotalCount/pageSize;
        int divideRest = rankingTotalCount%pageSize;
        if(divideRest!=0)	pageCount++;
        
        return new ResponseEntity<Object>(ATSPageReturnSet.builder()
													      .data(ATSPageResultSet.builder()
																		        .code(0)
																		        .enumCode("SUCCESS")
																		        .msg("30")
																		        .success(true)
																		        .count(rankingTotalCount)
																		        .pageCount(pageCount)
																		        .pageNo(pageNo)
																		        .pageSize(pageSize)
																		        .data(rankingLusirMovieList)
																		        .build())
													      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/ranking/tucao")
    public ResponseEntity<Object> getRankingTucaoMovieList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																					 , @RequestParam(value = "pageNo", required=false, defaultValue="1") int pageNo
    																					 , @RequestParam(value = "pageSize", required=false, defaultValue="30") int pageSize
    																					 , @RequestParam(value = "type", required=false, defaultValue="1") int type) {
    	 
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("pageNo", ((pageNo)-1)*pageSize);
    	map.put("pageSize", pageSize);
    	map.put("type", type);
    	
        List<ATSRanking> rankingTucaoMovieList = rankingService.getRankingTucaoMovieList(map,langCd);
        int rankingTotalCount = rankingService.getRankingTucaoMovieTotalCount(map);
        int pageCount = rankingTotalCount/pageSize;
        int divideRest = rankingTotalCount%pageSize;
        if(divideRest!=0)	pageCount++;
        
        return new ResponseEntity<Object>(ATSPageReturnSet.builder()
													      .data(ATSPageResultSet.builder()
																		        .code(0)
																		        .enumCode("SUCCESS")
																		        .msg("30")
																		        .success(true)
																		        .count(rankingTotalCount)
																		        .pageCount(pageCount)
																		        .pageNo(pageNo)
																		        .pageSize(pageSize)
																		        .data(rankingTucaoMovieList)
																		        .build())
													      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/ranking/actors")
    public ResponseEntity<Object> getRankingActorList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
																	    		    , @RequestParam(value = "pageNo", required=false, defaultValue="1") int pageNo
																				    , @RequestParam(value = "pageSize", required=false, defaultValue="30") int pageSize
																				    , @RequestParam(value = "type", required=false, defaultValue="1") int type) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("pageNo", ((pageNo)-1)*pageSize);
    	map.put("pageSize", pageSize);
    	map.put("type", type);
    	
        List<ATSActorRanking> rankingActorList = rankingService.getRankingActorList(map,langCd);
        int rankingTotalCount = rankingService.getRankingActorTotalCount(map);
        int pageCount = rankingTotalCount/pageSize;
        int divideRest = rankingTotalCount%pageSize;
        if(divideRest!=0)	pageCount++;

        return new ResponseEntity<Object>(ATSReturnSet.builder()
												      .data(ATSResultSet.builder()
																        .code(0)
																        .enumCode("SUCCESS")
																        .msg("")
																        .success(true)
																        .data(rankingActorList)
																        .build())
												      .build(), HttpStatus.OK);
    }
    
    
    
    @GetMapping("/search/hot/list")
    public ResponseEntity<Object> getSearchHotList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
																				 , @RequestParam(value = "pageSize", required=false, defaultValue="10") int pageSize) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("pageSize", pageSize);
    	map.put("langCd", langCd);
    	
        List<ATSMovieSearchHot> searchHotList = rankingService.getSearchHotList(map);

        return new ResponseEntity<Object>(ATSReturnSet.builder()
												      .data(ATSResultSet.builder()
																        .code(0)
																        .enumCode("SUCCESS")
																        .msg(searchHotList.size()+"")
																        .success(true)
																        .data(searchHotList)
																        .build())
												      .build(), HttpStatus.OK);
    }
	
}