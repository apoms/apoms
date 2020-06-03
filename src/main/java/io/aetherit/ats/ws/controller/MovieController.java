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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.aetherit.ats.ws.model.ATSPageResultSet;
import io.aetherit.ats.ws.model.ATSPageReturnSet;
import io.aetherit.ats.ws.model.ATSResultSet;
import io.aetherit.ats.ws.model.ATSReturnSet;
import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.actor.ATSActor;
import io.aetherit.ats.ws.model.actor.ATSActorDetail;
import io.aetherit.ats.ws.model.main.ATSCombine;
import io.aetherit.ats.ws.model.movie.ATSActorMovie;
import io.aetherit.ats.ws.model.movie.ATSMovie;
import io.aetherit.ats.ws.model.movie.ATSMovieBrowse;
import io.aetherit.ats.ws.model.movie.ATSNewMovie;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.service.AuthenticationService;
import io.aetherit.ats.ws.service.MovieService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
public class MovieController {
	
	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
	
	private MovieService movieService;
	private AuthenticationService authenticationService;
	
    @Autowired
    public MovieController(MovieService movieService, AuthenticationService authenticationService) {
        this.movieService = movieService;
        this.authenticationService = authenticationService;
    }
    
    
    @GetMapping("/home/newmov/query")
    public ResponseEntity<Object> getNewMovieList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd) {
        List<ATSNewMovie> resultlist = movieService.getNewMovieList(langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg("4")
										        .success(true)
										        .data(resultlist)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/home/combine")
    public ResponseEntity<Object> getCombine(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd) {
        List<ATSCombine> resultlist = movieService.getCombine(langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg("")
										        .success(true)
										        .data(resultlist)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/home/actor/list/newtop4")
    public ResponseEntity<Object> getNewTopActor(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd) {
        List<ATSActor> resultlist = movieService.getNewTopActor(langCd);
         
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									      .data(ATSResultSet.builder()
										        .code(0)
										        .enumCode("SUCCESS")
										        .msg("")
										        .success(true)
										        .data(resultlist)
										        .build())
									      .build(), HttpStatus.OK);
    }
    
    
    /**
     * 동영상 목록 : 조건 검색
     * @param httpRequest
     * @param pageNo
     * @param pageSize
     * @param selectionType
     * @param filter			: default :2, 1, 3
     * @param moduleId
     * @param localId			: location 구분
     * @param clsId
     * @param startMin			: 0, 30, 60, 120 영상 길이 필터 중 시작 길이
     * @param endMin			: 30, 60, 120 끝 길이
     * @param dayFrom			: 1, 7, 14, 30, 365 등록 일 수
     * @return
     */
    
    @GetMapping("/home/movlist")
    public ResponseEntity<Object> getMovieList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																		 , @RequestParam(value = "pageNo", required=true, defaultValue="1") int pageNo
    																		 , @RequestParam(value = "pageSize", required=true, defaultValue="10") int pageSize
																			 , @RequestParam(value = "selectionType", required=true, defaultValue="1") int selectionType
																			 , @RequestParam(value = "filter", required=true, defaultValue="2" ) int filter
																			 , @RequestParam(value = "clsId", required=true, defaultValue="1") int clsId
																			 , @RequestParam(value = "moduleId", required=false, defaultValue="0") int moduleId
																			 , @RequestParam(value = "localId", required=false, defaultValue="0") int localId
																			 , @RequestParam(value = "startMin", required=false, defaultValue="0") int startMin
																			 , @RequestParam(value = "endMin", required=false, defaultValue="0") int endMin
																			 , @RequestParam(value = "dayFrom", required=false, defaultValue="0") int dayFrom) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("page", ((pageNo)-1)*pageSize);
    	map.put("pageSize", pageSize);
    	map.put("selectionType", selectionType);
    	map.put("filter", filter);
    	map.put("moduleId", moduleId);
    	map.put("localId", localId);
    	map.put("clsId", clsId);
    	map.put("startMin", startMin);
    	map.put("endMin", endMin);
    	map.put("dayFrom", dayFrom);
    	
        List<ATSNewMovie> resultlist = movieService.getMovieList(map,langCd);
        int movieTotalCount = movieService.getMovieTotalCount(map);
        int pageCount = movieTotalCount/pageSize;
        int divideRest = movieTotalCount%pageSize;
        if(divideRest!=0)	pageCount++;
        
        return new ResponseEntity<Object>(ATSPageReturnSet.builder()
													      .data(ATSPageResultSet.builder()
																		        .code(0)
																		        .enumCode("SUCCESS")
																		        .msg(resultlist.size()+"")
																		        .success(true)
																		        .count(movieTotalCount)
																		        .pageCount(pageCount)
																		        .pageNo(pageNo)
																		        .pageSize(pageSize)
																		        .data(resultlist)
																		        .build())
													      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/es/mov/search")
    public ResponseEntity<Object> getSearchMovieList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
	    																		   , @RequestParam(value = "pageNo", required=true, defaultValue="1") int pageNo
	    																		   , @RequestParam(value = "pageSize", required=true, defaultValue="10") int pageSize
																				   , @RequestParam(value = "q", required=false, defaultValue="") String keyword) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("page", ((pageNo)-1)*pageSize);
    	map.put("pageSize", pageSize);
    	map.put("keyword", keyword);
    	
        List<ATSMovie> resultlist = movieService.getSearchMovieList(map,langCd);
        int searchMovieTotalCount = movieService.getSearchMovieTotalCount(map);
        int pageCount = searchMovieTotalCount/pageSize;
        int divideRest = searchMovieTotalCount%pageSize;
        if(divideRest!=0)	pageCount++;
        
        return new ResponseEntity<Object>(ATSPageReturnSet.builder()
													      .data(ATSPageResultSet.builder()
																		        .code(0)
																		        .enumCode("SUCCESS")
																		        .msg(resultlist.size()+"")
																		        .success(true)
																		        .count(searchMovieTotalCount)
																		        .pageCount(pageCount)
																		        .pageNo(pageNo)
																		        .pageSize(pageSize)
																		        .data(resultlist)
																		        .build())
													      .build(), HttpStatus.OK);
    }
    
    
    
    @GetMapping("/home/actor/detail")
    public ResponseEntity<Object> getActorDetail(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																		   , @RequestParam(value = "id", required=true, defaultValue="0") int id) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("id", id);
    	
    	List<ATSActorDetail> resultlist = movieService.getActorDetail(map,langCd);
        
        return new ResponseEntity<Object>(ATSReturnSet.builder()
												      .data(ATSResultSet.builder()
																        .code(0)
																        .enumCode("SUCCESS")
																        .msg("")
																        .success(true)
																        .data(resultlist)
																        .build())
												      .build(), HttpStatus.OK);
    }
    
    
    
    
    @GetMapping("/actor/movie/list")
    public ResponseEntity<Object> getActorMovieList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
	    																		  , @RequestParam(value = "id", required=true, defaultValue="0") int id
	    																		  , @RequestParam(value = "pageNo", required=true, defaultValue="1") int pageNo
	    																		  , @RequestParam(value = "pageSize", required=true, defaultValue="10") int pageSize
																				  , @RequestParam(value = "queryType", required=false, defaultValue="") String queryType) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("actorIdx", id);
    	map.put("page", ((pageNo)-1)*pageSize);
    	map.put("pageSize", pageSize);
    	map.put("queryType", queryType);
    	
        List<ATSActorMovie> resultlist = movieService.getActorMovieList(map,langCd);
        int actorMovieTotalCount = movieService.getActorMovieTotalCount(map);
        int pageCount = actorMovieTotalCount/pageSize;
        int divideRest = actorMovieTotalCount%pageSize;
        if(divideRest!=0)	pageCount++;
        
        return new ResponseEntity<Object>(ATSPageReturnSet.builder()
													      .data(ATSPageResultSet.builder()
																		        .code(0)
																		        .enumCode("SUCCESS")
																		        .msg(resultlist.size()+"")
																		        .success(true)
																		        .count(actorMovieTotalCount)
																		        .pageCount(pageCount)
																		        .pageNo(pageNo)
																		        .pageSize(pageSize)
																		        .data(resultlist)
																		        .build())
													      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/movie/actor/list")
    public ResponseEntity<Object> getMovieActorList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																			  , @RequestParam(value = "movieId", required=true) int movId) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("movId", movId);
    	
    	List<ATSActor> resultlist = movieService.getMovieActorList(map,langCd);
        
        return new ResponseEntity<Object>(ATSReturnSet.builder()
												      .data(ATSResultSet.builder()
																        .code(0)
																        .enumCode("SUCCESS")
																        .msg("")
																        .success(true)
																        .data(resultlist)
																        .build())
												      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/mov/browse2")
    public ResponseEntity<Object> getMovieBrowseList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																			   , @RequestParam(value = "version", required=true) String version
    																			   , @RequestParam(value = "movId", required=true) int movId) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("version", version);
    	map.put("movId", movId);
    	
    	List<ATSMovieBrowse> resultlist = movieService.getMovieBrowseList(map,langCd);
        
        return new ResponseEntity<Object>(ATSReturnSet.builder()
												      .data(ATSResultSet.builder()
																        .code(0)
																        .enumCode("SUCCESS")
																        .msg("")
																        .success(true)
																        .data(resultlist)
																        .build())
												      .build(), HttpStatus.OK);
    }
    
    
    @GetMapping("/es/mov/similar")
    public ResponseEntity<Object> getSimilarMovieList(HttpServletRequest httpRequest, @RequestHeader(value="lang-code") ATSLangCode langCd
    																				, @RequestParam(value = "movId", required=true) int movId) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("movId", movId);
    	
        List<ATSMovie> resultlist = movieService.getSimilarMovieList(map,langCd);
        
        return new ResponseEntity<Object>(ATSReturnSet.builder()
									        		  .data(ATSResultSet.builder()
																        .code(0)
																        .enumCode("SUCCESS")
																        .msg("")
																        .success(true)
																        .data(resultlist)
																        .build())
													   .build(), HttpStatus.OK);
    }
    
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PatchMapping("/mov/getUpDown2")
    public ResponseEntity<Void> getMovieUpDown(HttpServletRequest httpRequest, @RequestParam(value="movId", required=true) int movId
    																	 	 , @RequestParam(value="channel", required=true) int channel){
    	ATSSimpleUser user = authenticationService.getUser();
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("movId", movId);
    	map.put("userId", user.getUserId());
    	map.put("channel", channel);
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PatchMapping("/mov/up")
    public ResponseEntity<Void> setMovieUp(HttpServletRequest httpRequest, @RequestParam(value="movId", required=true) int movId
    																	 , @RequestParam(value="channel", required=true) int channel){
    	
    	ATSSimpleUser user = authenticationService.getUser();
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("movId", movId);
    	map.put("channel", channel);
    	
    	movieService.setMovieUp(map, user.getUserId());
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @ApiImplicitParams({
        @ApiImplicitParam(name = "x-auth-token", value = "", required = false, dataType = "String", paramType = "header")
    })
    @PatchMapping("/mov/down")
    public ResponseEntity<Void> setMovieDown(HttpServletRequest httpRequest, @RequestParam(value="movId", required=true) int movId
    																	 , @RequestParam(value="channel", required=true) int channel){
    	ATSSimpleUser user = authenticationService.getUser();
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("movId", movId);
    	map.put("channel", channel);
    	
    	movieService.setMovieDown(map, user.getUserId());
    	return new ResponseEntity<>(HttpStatus.OK);
    }
	
}