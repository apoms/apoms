package io.aetherit.ats.ws.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.aetherit.ats.ws.model.actor.ATSActor;
import io.aetherit.ats.ws.model.actor.ATSActorDTO;
import io.aetherit.ats.ws.model.actor.ATSActorDetail;
import io.aetherit.ats.ws.model.actor.ATSSimpleActor;
import io.aetherit.ats.ws.model.dao.ATSMovieActorBas;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieCoverImage;
import io.aetherit.ats.ws.model.dao.ATSMovieReactionTxn;
import io.aetherit.ats.ws.model.dao.ATSMovieTagDtl;
import io.aetherit.ats.ws.model.main.ATSCombine;
import io.aetherit.ats.ws.model.movie.ATSActorMovie;
import io.aetherit.ats.ws.model.movie.ATSCover;
import io.aetherit.ats.ws.model.movie.ATSMovie;
import io.aetherit.ats.ws.model.movie.ATSMovieBrowse;
import io.aetherit.ats.ws.model.movie.ATSMovieTag;
import io.aetherit.ats.ws.model.movie.ATSNewMovie;
import io.aetherit.ats.ws.model.movie.ATSRanking;
import io.aetherit.ats.ws.model.movie.ATSRecommand;
import io.aetherit.ats.ws.model.movie.ATSWonderful;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.model.type.ATSReactionType;
import io.aetherit.ats.ws.repository.MovieRepository;

@Service
public class MovieService {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    private CommonService commonService;
    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, CommonService commonService) {
        this.movieRepository = movieRepository;
        this.commonService = commonService;
    }

    /**
     * 신규동영상 목록 조회
     * @param userId
     * @return
     */
    public List<ATSNewMovie> getNewMovieList(ATSLangCode langCd) {
    	List<ATSNewMovie> returnList = new ArrayList<ATSNewMovie>();
    	
    	List<ATSMovieBas> movieList = movieRepository.selectNewMovieList();
    	
    	for(ATSMovieBas movieBas:movieList) {
    		HashMap<String,String> address = new HashMap<String,String>();
    		address.put("480P", movieBas.getP480());
    		
    		ATSCover allCovers = commonService.getCover(movieBas.getMovId(), langCd);
    		List<ATSMovieTag> relTagList = commonService.getRelTagList(movieBas.getMovId(), langCd);
    		
//    		HashMap<String,Object> coverMap = new HashMap<String,Object>();
//			coverMap.put("movId", movieBas.getMovId());
//			coverMap.put("langCd", langCd);
    		
//    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(coverMap);
    		
    		
//    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
//    			if(movieCoverImage.getLangCd()==langCd) {
//    				allCovers = ATSCover.builder()								
//    							.horizontal_large(movieCoverImage.getHorizontalLarge())
//    							.horizontal_small(movieCoverImage.getHorizontalSmall())
//    							.vertical_large(movieCoverImage.getVerticalLarge())
//    							.vertical_small(movieCoverImage.getVerticalSmall())
//    						    .build();
//    			}
//    		}
    		
//    		List<ATSMovieTag> relTagList = new ArrayList<ATSMovieTag>();
    		
//    		for(ATSMovieTagDtl movieTagDtl : movieRepository.selectMovieTagDtlList(map)){
//    			ATSMovieTag relTag = ATSMovieTag.builder()
//    											.id(movieTagDtl.getId())
//    											.name(movieTagDtl.getName())
//    											.build();
//    			relTagList.add(relTag);
//    		}
    		
    		ATSNewMovie newMovie = ATSNewMovie.builder()
    							   .address(address)
    							   .allCovers(allCovers)
//    							   .allJCovers(allJCovers)					// 삭제
//    							   .cover(null)								// 삭제
    							   .hasDownload(movieBas.isHasDown())
    							   .hasFavor(movieBas.isHasLove())
    							   .id(movieBas.getMovId())
//    							   .jCover(null)							// 삭제
//    							   .jThumbnail(null)						// 삭제
    							   .mins(movieBas.getMins())
    							   .movName(movieBas.getMovName())
    							   .movScore(movieBas.getMovScore()+"")
//    							   .movSize(null)							// 삭제
    							   .movSnOri(movieBas.getMovSnOri())	
    							   .playCnt(movieBas.getPlayCnt())
    							   .playCntStr(movieBas.getPlayCntStr())	// playCnt 를 언어별로 다뉘처리
    							   .relTag(relTagList)							 
//    							   .thumbnail(null)					// 삭제
    							   .userId(movieBas.getGmtCreateId())		// 영상 생성자 ID
    							   .vipFlag(0)
    							   .build();
    		
    		returnList.add(newMovie);
    	}
    	
        return returnList;
    }
    
    
    /**
     * 전체 조회
     * @param userId
     * @return
     */
    public List<ATSCombine> getCombine(ATSLangCode langCd) {
    	List<ATSCombine> returnList = new ArrayList<ATSCombine>();
    	
    	List<ATSActor> recommandActorList  = setRecommendActorList(langCd);
        List<ATSRanking> todayRankingdMov  = setTodayRankingdMov(langCd);
        List<ATSRecommand> todayRecommandMov  = setTodayRecommandMov(langCd);
        List<ATSWonderful> wonderfulMov	 = setWonderfulMov(langCd);
    	
        ATSCombine combine = ATSCombine.builder()
        					 		   .recommandActorList(recommandActorList)
        					 		   .todayRankingdMov(todayRankingdMov)
        					 		   .todayRecommandMov(todayRecommandMov)
        					 		   .wonderfulMov(wonderfulMov)
        							   .build();
        returnList.add(combine);
        
    	return returnList;
    }
    
    
    /**
     * recommandActorList Set	2명
     * @return
     */
    private List<ATSActor> setRecommendActorList(ATSLangCode langCd) {
        List<ATSActor> returnList  = new ArrayList<ATSActor>();
        
        HashMap<String,Object> paraMap = new HashMap<String,Object>();
    	paraMap.put("pageSize", 4);
        List<ATSMovieActorBas> recommendActors = movieRepository.selectRecommendActorList(paraMap);
        
    	for(ATSMovieActorBas recommendActor:recommendActors) {
    		
    		HashMap<String,Object> map = new HashMap<String,Object>();
    		map.put("actorIdx", recommendActor.getActorIdx());
    		map.put("pageSize", 30);
    		
    		ATSActorDTO actor = ATSActorDTO.builder()
    							.briefIntroduction(recommendActor.getBriefIntroduction())
    							.id(recommendActor.getActorIdx())
    							.nameCn(recommendActor.getActorNameCn())
    							.nameEn(recommendActor.getActorNameEn())
    							.nameJpn(recommendActor.getActorNameJp())
    							.photoUrl(recommendActor.getPhotoUrl())
    							.videosCount(recommendActor.getVideosCount())
    							.build();
    		
    		List<ATSActorMovie> movieList = new ArrayList<ATSActorMovie>();
    		
    		List<ATSMovieBas> movieBasList = movieRepository.selectActorMovieList(map);
    		
    		for(ATSMovieBas movieBas:movieBasList) {
    			
    			HashMap<String,String> address = new HashMap<String,String>();
        		address.put("480P", movieBas.getP480());
        		
        		ATSCover cover = commonService.getCover(movieBas.getMovId(), langCd);
    			
/*    			
    			HashMap<String,Object> coverMap = new HashMap<String,Object>();
    			coverMap.put("movId", movieBas.getMovId());
    			coverMap.put("langCd", langCd);
    			
    			HashMap<String,String> address = new HashMap<String,String>();
        		address.put("480P", movieBas.getP480());
        		
        		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(coverMap);
        		
        		ATSCover cover = new ATSCover();
        		
        		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
        			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
        				cover = ATSCover.builder()
        						.horizontal_large(movieCoverImage.getHorizontalLarge())
        						.horizontal_small(movieCoverImage.getHorizontalSmall())
        						.vertical_large(movieCoverImage.getVerticalLarge())
        						.vertical_small(movieCoverImage.getVerticalSmall())
        						.build();
        			}
        		}
*/        		
        		ATSActorMovie recommandActorMovie = ATSActorMovie.builder()
											    				 .address(address)
											    				 .cover(cover)
											    				 .id(movieBas.getMovId())
//											    				 .jThumbnail("/2.jpg")						// 삭제
											    				 .mins(movieBas.getMins())
											    				 .movName(movieBas.getMovName())			// 언어별로 영상명 넣어줌 
											    				 .movScore(movieBas.getMovScore()+"")
//											    				 .movSize(null)								// 삭제
											    				 .movSnOri(movieBas.getMovSnOri())
//											    				 .primevalCover(primevalCover)				// 삭제
											    				 .strPlayCnt(movieBas.getPlayCntStr())		// 언어별로 처리
//											    				 .thumbnail("/e.webp")						//삭제
											    				 .vipFlag(0)								// 추후 파악되면 추가
											    				 .build();
        		movieList.add(recommandActorMovie);
    		}
    		ATSActor recommandActor = ATSActor.builder()
						    				  .actorDTO(actor)
						    				  .movieList(movieList)
    										  .build();
    		returnList.add(recommandActor);
    	}
    	return returnList;
    }
    
    /**
	 * todayRankingdMov Set
	 */
    private List<ATSRanking> setTodayRankingdMov(ATSLangCode langCd) {
        List<ATSRanking> returnList  = new ArrayList<ATSRanking>();
		
        HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("pageSize", 10);
        
		List<ATSMovieBas> todayRankingList = movieRepository.selectTodayRankingList(map);
		
		for(ATSMovieBas movieBas:todayRankingList) {

			HashMap<String,String> address = new HashMap<String,String>();
    		address.put("480P", movieBas.getP480());
 
    		/*
    		HashMap<String,Object> coverMap = new HashMap<String,Object>();
    		coverMap.put("movId", movieBas.getMovId());
    		coverMap.put("langCd", langCd);
    		
    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(coverMap);
    		
    		ATSCover url = new ATSCover();
    		
    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
    			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
    				url = ATSCover.builder()
						  .horizontal_large(movieCoverImage.getHorizontalLarge())
						  .horizontal_small(movieCoverImage.getHorizontalSmall())
						  .vertical_large(movieCoverImage.getVerticalLarge())
						  .vertical_small(movieCoverImage.getVerticalSmall())
						  .build();
    			}
    		}
    		*/
    		ATSCover url = commonService.getCover(movieBas.getMovId(), langCd);
    		
    		/**
    		 * 랭킹은 평가구분으로 그룹빠이 해서 카운트
    		 */
    		ATSRanking todayRanking = ATSRanking.builder()
							    				.address(address)
							    				.arrowState(1)									// TODO : 어디서 가져오는 지 확인 피료    : 기준 정해진 후 적용
							    				.collectNumber(movieBas.getPlayCnt())			// 플레이 수 ?
							    				.downloadNumber(movieBas.getDownCnt())			// 다운로드 수?
							    				.mins(movieBas.getMins())
							    				.movId(movieBas.getMovId())
//							    				.movSize(null)									// 삭제
							    				.mov_index(1)									// TODO : 어디서 가져오는 지 확인 피료 : 확인 후 적용
							    				.name(movieBas.getMovName())
//							    				.oriUrl(oriUrl)									// 삭제
							    				.playNumber(movieBas.getPlayCnt())
							    				.ranking(1)
							    				.release_time(movieBas.getMins().getMinute())	// 현재시간에서 업로드 시간을 뺀 값으로 int
							    				.type(movieBas.getMovType())										// TODO : 어디서 가져오는 지 확인 피료 : 확인후 적용
							    				.url(url)
							    				.vipFlag(0)
							    				.build();
    		returnList.add(todayRanking);
		}
    	return returnList;
    }
    
    
    /**
	 * todayRecommandMov Set
	 */
    private List<ATSRecommand> setTodayRecommandMov(ATSLangCode langCd) {
        List<ATSRecommand> returnList  = new ArrayList<ATSRecommand>();
		
        HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("pageSize", 10);
        
		List<ATSMovieBas> todayRecommandList = movieRepository.selectTodayRecommandList(map);
		
		for(ATSMovieBas movieBas:todayRecommandList) {
			
			HashMap<String,String> address = new HashMap<String,String>();
    		address.put("480P", movieBas.getP480());

    		/*
			HashMap<String,Object> coverMap = new HashMap<String,Object>();
			coverMap.put("movId", movieBas.getMovId());
			coverMap.put("langCd", langCd);
    		
    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(coverMap);
    		
    		ATSCover allCovers = new ATSCover();
    		
    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
    			if(movieCoverImage.getLangCd()==langCd) {
    				allCovers = ATSCover.builder()
									    .horizontal_large(movieCoverImage.getHorizontalLarge())
									    .horizontal_small(movieCoverImage.getHorizontalSmall())
									    .vertical_large(movieCoverImage.getVerticalLarge())
									    .vertical_small(movieCoverImage.getVerticalSmall())
									    .build();
    			}
    		}
*/  		
    		ATSCover allCovers = commonService.getCover(movieBas.getMovId(), langCd);
    		List<ATSMovieTag> relTagName = commonService.getRelTagList(movieBas.getMovId(), langCd);
    		
    		ATSRecommand todayRecommand = ATSRecommand.builder()
													  .actionNum(7)						// TODO : 어디서 가져오는 지 확인 피료
								    				  .address(address)
								    				  .allCovers(allCovers)
//								    				  .allJCovers(allJCovers)
								    				  .faceNum(7)						// TODO : 어디서 가져오는 지 확인 피료
								    				  .id(movieBas.getGmtCreateId())							// 생성자 아이디
								    				  .movId(movieBas.getMovId())
								    				  .movName(movieBas.getMovName())
								    				  .recommendDate(null)				// TODO : 어디서 가져오는 지 확인 피료
								    				  .recommendDesc("")				// TODO : 어디서 가져오는 지 확인 피료
								    				  .relTagName(relTagName)			// 上同
								    				  .storyNum(7)						// TODO : 어디서 가져오는 지 확인 피료
								    				  .build();
    		returnList.add(todayRecommand);
		}
    	return returnList;
    }
    
    /**
	 * wonderfulMov Set	 : 1일기준 좋아요 수 많은 순서 위에 10개 
	 */
    private List<ATSWonderful> setWonderfulMov(ATSLangCode langCd) {			
        List<ATSWonderful> returnList  = new ArrayList<ATSWonderful>();
		
        HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("pageSize", 10);
        
		List<ATSMovieBas> wonderfulList = movieRepository.selectWonderfulMovieList(map);
		
		for(ATSMovieBas movieBas:wonderfulList) {
			HashMap<String,String> address = new HashMap<String,String>();
    		address.put("480P", movieBas.getP480());
    		
    		ATSWonderful wonderful = ATSWonderful.builder()
    											 .address(address)
    											 .aliasName(null)						// TODO : 어디서 가져오는 지 확인 피료
    											 .browCntStr(movieBas.getPlayCnt())		// TODO : 어디서 가져오는 지 확인 피료 (맞나?)
    											 .cover("/1.webp")						// 上同
    											 .disCnt(movieBas.getDissCnt()+"")		// TODO : 데이타 형식 확인 피료
    											 .gmtCreate(movieBas.getGmtCreate())	
    											 .hasUp(movieBas.isHasUp())		
    											 .icon("/icon/8.jpg")					// TODO : 어디서 가져오는 지 확인 피료
    											 .id(movieBas.getGmtCreateId())			// 생성자 아이디
    											 .mins(movieBas.getMins())
    											 .movId(movieBas.getMovId())
    											 .myInviteCode("VDV6AU")
    											 .name(movieBas.getMovName())
    											 .originalMovid(-1)						// TODO : 어디서 가져오는 지 확인 피료
    											 .primevalCover("/1.gif")				// 삭제
    											 .size(2839600)							// 삭제
    											 .thumbnail("/1.png")					// 삭제
    											 .type(Integer.parseInt(movieBas.getMovType()))		// TODO : 추후 타입 맟출 것						// 영상 타입
    											 .uId(7748167)							// TODO : 어디서 가져오는 지 확인 피료
    											 .upCnt(movieBas.getUpCnt())
							    				 .build();
    		returnList.add(wonderful);
		}
    	return returnList;
    }
    
    
    /**
     * new top actor : 4
     * @return
     */
    public List<ATSActor> getNewTopActor(ATSLangCode langCd) {
    	List<ATSActor> returnList  = new ArrayList<ATSActor>();
    	
    	HashMap<String,Object> paraMap = new HashMap<String,Object>();
    	paraMap.put("pageSize", 4);
    	
    	/**
    	 * 총 출연작의 좋아요 수로 순위 매겨서 4명만
    	 */
        List<ATSMovieActorBas> newTopActors = movieRepository.selectNewTopActorList(paraMap);
        
    	for(ATSMovieActorBas newTopActor:newTopActors) {
    		
    		HashMap<String,Object> map = new HashMap<String,Object>();
    		map.put("actorIdx", newTopActor.getActorIdx());
    		map.put("pageSize", 30);
    		
    		ATSActorDTO actor = ATSActorDTO.builder()
    							.briefIntroduction(newTopActor.getBriefIntroduction())
    							.id(newTopActor.getActorIdx())
    							.nameCn(newTopActor.getActorNameCn())
    							.nameEn(newTopActor.getActorNameEn())
    							.nameJpn(newTopActor.getActorNameJp())
    							.photoUrl(newTopActor.getPhotoUrl())
    							.videosCount(newTopActor.getVideosCount())
    							.build();
    		
    		List<ATSActorMovie> movieList = new ArrayList<ATSActorMovie>();
    		
    		/**
    		 * 최신작 30개
    		 */
    		List<ATSMovieBas> movieBasList = movieRepository.selectActorMovieList(map);
    		
    		for(ATSMovieBas movieBas:movieBasList) {
    			HashMap<String,String> address = new HashMap<String,String>();
        		address.put("480P", movieBas.getP480());

/*
        		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
        		
        		ATSCover cover = new ATSCover();
        		ATSCover primevalCover = new ATSCover();
        		
        		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
        			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
        				cover = ATSCover.builder()
        						.horizontal_large(movieCoverImage.getHorizontalLarge())
        						.horizontal_small(movieCoverImage.getHorizontalSmall())
        						.vertical_large(movieCoverImage.getVerticalLarge())
        						.vertical_small(movieCoverImage.getVerticalSmall())
        						.build();
        			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
        				primevalCover = ATSCover.builder()
		        						.horizontal_large(movieCoverImage.getHorizontalLarge())
		        						.horizontal_small(movieCoverImage.getHorizontalSmall())
		        						.vertical_large(movieCoverImage.getVerticalLarge())
		        						.vertical_small(movieCoverImage.getVerticalSmall())
		        						.build();
        			}
        		}
*/        		
        		ATSCover cover = commonService.getCover(movieBas.getMovId(), langCd);
        		
        		ATSActorMovie recommandActorMovie = ATSActorMovie.builder()
											    				 .address(address)
											    				 .cover(cover)
											    				 .id(movieBas.getMovId())
											    				 .jThumbnail("/2.jpg")		// TODO : 어디서 가져오는 지 확인 피료
											    				 .mins(movieBas.getMins())
											    				 .movName(movieBas.getMovName())
											    				 .movScore(movieBas.getMovScore()+"")
											    				 .movSize(null)				// TODO : 어디서 가져오는 지 확인 피료
											    				 .movSnOri(movieBas.getMovSnOri())
//											    				 .primevalCover(primevalCover)
											    				 .strPlayCnt(movieBas.getPlayCntStr())
											    				 .thumbnail("/e.webp")		// TODO : 어디서 가져오는 지 확인 피료
											    				 .vipFlag(0)
											    				 .build();
        		movieList.add(recommandActorMovie);
    		}
    		ATSActor newActor = ATSActor.builder()
				    				    .actorDTO(actor)
				    				    .movieList(movieList)
									    .build();
    		returnList.add(newActor);
    	}
    	return returnList;
    }
    
    
    
    
    /**
     * 영상 목록 조회 : 조건 검색
     * @param userId
     * @return
     */
    public List<ATSNewMovie> getMovieList(HashMap<String,Object> map, ATSLangCode langCd) {
    	List<ATSNewMovie> returnList = new ArrayList<ATSNewMovie>();
    	
    	List<ATSMovieBas> movieList = movieRepository.selectMovieList(map);
    	
    	for(ATSMovieBas movieBas:movieList) {
    		HashMap<String,String> address = new HashMap<String,String>();
    		address.put("480P", movieBas.getP480());
/*    		
    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
    		
    		ATSCover allCovers = new ATSCover();
    		ATSCover allJCovers = new ATSCover();
    		
    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
    			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
    				allCovers = ATSCover.builder()
    							.horizontal_large(movieCoverImage.getHorizontalLarge())
    							.horizontal_small(movieCoverImage.getHorizontalSmall())
    							.vertical_large(movieCoverImage.getVerticalLarge())
    							.vertical_small(movieCoverImage.getVerticalSmall())
    						    .build();
    			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
    				allJCovers = ATSCover.builder()
							.horizontal_large(movieCoverImage.getHorizontalLarge())
							.horizontal_small(movieCoverImage.getHorizontalSmall())
							.vertical_large(movieCoverImage.getVerticalLarge())
							.vertical_small(movieCoverImage.getVerticalSmall())
						    .build();
    			}
    		}
*/    		
    		ATSCover allCovers = commonService.getCover(movieBas.getMovId(), langCd);
    		List<ATSMovieTag> relTag = commonService.getRelTagList(movieBas.getMovId(), langCd);
    		
    		ATSNewMovie movie = ATSNewMovie.builder()
    							   .address(address)
    							   .allCovers(allCovers)
//    							   .allJCovers(allJCovers)
//    							   .cover("/5.webp")			// TODO : 어디서 가져오는 지 확인 피료
    							   .hasDownload(movieBas.isHasDown())
    							   .hasFavor(movieBas.isHasLove())	
    							   .id(movieBas.getMovId())
//    							   .jCover("/1.jpg")			// TODO : 어디서 가져오는 지 확인 피료
//    							   .jThumbnail("/2.jpg")		// TODO : 어디서 가져오는 지 확인 피료
    							   .mins(movieBas.getMins())
    							   .movName(movieBas.getMovName())
    							   .movScore(movieBas.getMovScore()+"")
//    							   .movSize(null)				// TODO : 어디서 가져오는 지 확인 피료
    							   .movSnOri(movieBas.getMovSnOri())
    							   .playCnt(movieBas.getPlayCnt())
    							   .playCntStr(movieBas.getPlayCntStr())
    							   .relTag(relTag)
//    							   .thumbnail("/5.webp")		// TODO : 어디서 가져오는 지 확인 피료
    							   .userId(movieBas.getGmtCreateId())					// TODO : 어디서 가져오는 지 확인 피료
    							   .vipFlag(0)
    							   .build();
    		
    		returnList.add(movie);
    	}
    	
        return returnList;
    }
    
    
    public int getMovieTotalCount(HashMap<String,Object> map) {
		return movieRepository.selectMovieTotalCount(map);
	}
    
    
    /**
     * 영상 목록 조회 : 조건 검색 (키워드 검색) : 영상별 키워드 목록에서 조회
     * @param userId
     * @return
     */
    public List<ATSMovie> getSearchMovieList(HashMap<String,Object> map, ATSLangCode langCd) {
    	List<ATSMovie> returnList = new ArrayList<ATSMovie>();
    	
    	List<ATSMovieBas> movieList = movieRepository.selectSearchMovieList(map);
    	
    	for(ATSMovieBas movieBas:movieList) {
    		
    		HashMap<String,String> address = new HashMap<String,String>();
    		address.put("240P", movieBas.getP240());
    		address.put("360P", movieBas.getP360());
    		address.put("480P", movieBas.getP480());
    		address.put("720P", movieBas.getP720());
    		address.put("1080P", movieBas.getP1080());

/*    		
    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
    		
    		ATSCover allCovers = new ATSCover();
    		ATSCover allJCovers = new ATSCover();
    		
    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
    			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
    				allCovers = ATSCover.builder()
    							.horizontal_large(movieCoverImage.getHorizontalLarge())
    							.horizontal_small(movieCoverImage.getHorizontalSmall())
    							.vertical_large(movieCoverImage.getVerticalLarge())
    							.vertical_small(movieCoverImage.getVerticalSmall())
    						    .build();
    			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
    				allJCovers = ATSCover.builder()
							.horizontal_large(movieCoverImage.getHorizontalLarge())
							.horizontal_small(movieCoverImage.getHorizontalSmall())
							.vertical_large(movieCoverImage.getVerticalLarge())
							.vertical_small(movieCoverImage.getVerticalSmall())
						    .build();
    			}
    		}
*/
    		ATSCover allCovers = commonService.getCover(movieBas.getMovId(), langCd);
    		List<ATSMovieTag> relTagName = commonService.getRelTagList(movieBas.getMovId(), langCd);
    		
    		ATSMovie movie = ATSMovie.builder()
    							   .address(address)
    							   .allCovers(allCovers)
//    							   .allJCovers(allJCovers)
//    							   .cover("/5.webp")						// TODO : 어디서 가져오는 지 확인 피료
    							   .id(movieBas.getMovId())
//    							   .jThumbnail("/5.jpg")					// TODO : 어디서 가져오는 지 확인 피료
    							   .mins(movieBas.getMins())
    							   .movName(movieBas.getMovName())
    							   .movScore(movieBas.getMovScore()+"")
//    							   .movSize(null)							// TODO : 어디서 가져오는 지 확인 피료
    							   .movSn(movieBas.getMovSn())
    							   .movSnOri(movieBas.getMovSnOri())
    							   .playCnt(movieBas.getPlayCnt())
//    							   .primevalCover("/1.jpg")					// TODO : 어디서 가져오는 지 확인 피료
    							   .relTagName(relTagName)
    							   .score(0)								// TODO : 어디서 가져오는 지 확인 피료
    							   .selector("")							// TODO : 어디서 가져오는 지 확인 피료
    							   .strPlayCnt(movieBas.getPlayCntStr())
//    							   .thumbnail("/5.jpg")						// TODO : 어디서 가져오는 지 확인 피료
    							   .userId(movieBas.getGmtCreateId())
    							   .vipFlag(0)								// TODO : 어디서 가져오는 지 확인 피료
    							   .build();
    		
    		returnList.add(movie);
    	}
        return returnList;
    }
    
    
    public int getSearchMovieTotalCount(HashMap<String,Object> map) {
		return movieRepository.selectSearchMovieTotalCount(map);
	}
    
    
    
    
    public List<ATSActorDetail> getActorDetail(HashMap<String,Object> map, ATSLangCode langCd) {
        List<ATSActorDetail> returnList  = new ArrayList<ATSActorDetail>();
        
        ATSMovieActorBas actorDetail = movieRepository.selectActorDetail(map);
        
        ATSActorDetail actor = ATSActorDetail.builder()
											 .briefIntroduction(actorDetail.getBriefIntroduction())
											 .bust(actorDetail.getBust())
											 .cup(actorDetail.getCup())
											 .height(actorDetail.getHeight())
											 .hips(actorDetail.getHips())
											 .waist(actorDetail.getWaist())
											 .id(actorDetail.getActorIdx())
											 .nameCn(actorDetail.getActorNameCn())
											 .nameEn(actorDetail.getActorNameEn())
											 .nameJpn(actorDetail.getActorNameJp())
											 .photoUrl(actorDetail.getPhotoUrl())
											 .videosCount(actorDetail.getVideosCount())
											 .build();
    		
        returnList.add(actor);
    	return returnList;
    }
    
    
    
    public List<ATSActorMovie> getActorMovieList(HashMap<String,Object> map, ATSLangCode langCd) {
    	List<ATSActorMovie> returnList  = new ArrayList<ATSActorMovie>();
    		
		List<ATSMovieBas> movieBasList = movieRepository.selectActorMovieList(map);
		
		for(ATSMovieBas movieBas:movieBasList) {
			HashMap<String,String> address = new HashMap<String,String>();
			address.put("240P", movieBas.getP240());
    		address.put("360P", movieBas.getP360());
    		address.put("480P", movieBas.getP480());
    		address.put("720P", movieBas.getP720());
    		address.put("1080P", movieBas.getP1080());

/*    		
    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
    		
    		ATSCover cover = new ATSCover();
    		ATSCover primevalCover = new ATSCover();
    		
    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
    			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
    				cover = ATSCover.builder()
    						.horizontal_large(movieCoverImage.getHorizontalLarge())
    						.horizontal_small(movieCoverImage.getHorizontalSmall())
    						.vertical_large(movieCoverImage.getVerticalLarge())
    						.vertical_small(movieCoverImage.getVerticalSmall())
    						.build();
    			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
    				primevalCover = ATSCover.builder()
	        						.horizontal_large(movieCoverImage.getHorizontalLarge())
	        						.horizontal_small(movieCoverImage.getHorizontalSmall())
	        						.vertical_large(movieCoverImage.getVerticalLarge())
	        						.vertical_small(movieCoverImage.getVerticalSmall())
	        						.build();
    			}
    		}
*/
    		ATSCover cover = commonService.getCover(movieBas.getMovId(), langCd);
    		
    		ATSActorMovie actorMovie = ATSActorMovie.builder()
								    				.address(address)
								    				.cover(cover)
								    				.id(movieBas.getMovId())
//								    				.jThumbnail("/2.jpg")						// TODO : 어디서 가져오는 지 확인 피료
								    				.mins(movieBas.getMins())
								    				.movName(movieBas.getMovName())
								    				.movScore(movieBas.getMovScore()+"")
//								    				.movSize(null)								// TODO : 어디서 가져오는 지 확인 피료
								    				.movSnOri(movieBas.getMovSnOri())
//								    				.primevalCover(primevalCover)
								    				.strPlayCnt(movieBas.getPlayCntStr())
//								    				.thumbnail("/e.webp")						// TODO : 어디서 가져오는 지 확인 피료
								    				.vipFlag(0)									// TODO : 어디서 가져오는 지 확인 피료
								    				.build();
    		returnList.add(actorMovie);
		}
    	return returnList;
    }
    
    public int getActorMovieTotalCount(HashMap<String,Object> map) {
		return movieRepository.selectActorMovieTotalCount(map);
	}
    
    
    /**
     * movieActorList
     * @return
     */
    public List<ATSActor> getMovieActorList(HashMap<String,Object> map, ATSLangCode langCd) {
        List<ATSActor> returnList  = new ArrayList<ATSActor>();
        
        List<ATSMovieActorBas> movieActors = movieRepository.selectMovieActorList(map);
        
    	for(ATSMovieActorBas actor:movieActors) {
    		
    		HashMap<String,Object> paraMap = new HashMap<String,Object>();
    		map.put("actorIdx", actor.getActorIdx());
    		map.put("pageSize", 30);
    		
    		ATSActorDTO actorDto = ATSActorDTO.builder()
    							.briefIntroduction(actor.getBriefIntroduction())
    							.id(actor.getActorIdx())
    							.nameCn(actor.getActorNameCn())
    							.nameEn(actor.getActorNameEn())
    							.nameJpn(actor.getActorNameJp())
    							.photoUrl(actor.getPhotoUrl())
    							.videosCount(actor.getVideosCount())
    							.build();
    		
    		List<ATSActorMovie> movieList = new ArrayList<ATSActorMovie>();
    		
    		List<ATSMovieBas> movieBasList = movieRepository.selectActorMovieList(paraMap);
    		
    		for(ATSMovieBas movieBas:movieBasList) {
    			HashMap<String,String> address = new HashMap<String,String>();
        		address.put("480P", movieBas.getP480());

/*
        		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
        		
        		ATSCover cover = new ATSCover();
        		ATSCover primevalCover = new ATSCover();
        		
        		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
        			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
        				cover = ATSCover.builder()
        						.horizontal_large(movieCoverImage.getHorizontalLarge())
        						.horizontal_small(movieCoverImage.getHorizontalSmall())
        						.vertical_large(movieCoverImage.getVerticalLarge())
        						.vertical_small(movieCoverImage.getVerticalSmall())
        						.build();
        			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
        				primevalCover = ATSCover.builder()
		        						.horizontal_large(movieCoverImage.getHorizontalLarge())
		        						.horizontal_small(movieCoverImage.getHorizontalSmall())
		        						.vertical_large(movieCoverImage.getVerticalLarge())
		        						.vertical_small(movieCoverImage.getVerticalSmall())
		        						.build();
        			}
        		}
*/
        		ATSCover cover = commonService.getCover(movieBas.getMovId(), langCd);
        		
        		ATSActorMovie actorMovie = ATSActorMovie.builder()
											    				 .address(address)
											    				 .cover(cover)
											    				 .id(movieBas.getMovId())
//											    				 .jThumbnail("/2.jpg")		// TODO : 어디서 가져오는 지 확인 피료
											    				 .mins(movieBas.getMins())
											    				 .movName(movieBas.getMovName())
											    				 .movScore(movieBas.getMovScore()+"")
//											    				 .movSize(null)				// TODO : 어디서 가져오는 지 확인 피료
											    				 .movSnOri(movieBas.getMovSnOri())
//											    				 .primevalCover(primevalCover)
											    				 .strPlayCnt(movieBas.getPlayCntStr())
//											    				 .thumbnail("/e.webp")		// TODO : 어디서 가져오는 지 확인 피료
											    				 .vipFlag(0)
											    				 .build();
        		movieList.add(actorMovie);
    		}
    		ATSActor movieActor = ATSActor.builder()
					    				  .actorDTO(actorDto)
					    				  .movieList(movieList)
										  .build();
    		returnList.add(movieActor);
    	}
    	return returnList;
    }
    
    
    /**
     * movieActorList
     * @return
     */
    public List<ATSMovieBrowse> getMovieBrowseList(HashMap<String,Object> map, ATSLangCode langCd) {
        List<ATSMovieBrowse> returnList  = new ArrayList<ATSMovieBrowse>();
        
		ATSMovieBas movieBas = movieRepository.selectMovieBas(map);
			
		List<ATSSimpleActor> actorList  = new ArrayList<ATSSimpleActor>();
		
		List<ATSMovieActorBas> movieActors = movieRepository.selectMovieActorList(map);
        
    	for(ATSMovieActorBas actor:movieActors) {
    		
    		ATSSimpleActor simpleActor = ATSSimpleActor.builder()
						    						   .id(actor.getActorIdx())
						    						   .nameCn(actor.getActorNameCn())
						    						   .nameEn(actor.getActorNameEn())
						    						   .nameJpn(actor.getActorNameJp())
						    						   .photoUrl(actor.getPhotoUrl())
						    						   .build();
    		
    		actorList.add(simpleActor);
    	}
		
		HashMap<String,String> address = new HashMap<String,String>();
		address.put("240P", movieBas.getP240());
		address.put("360P", movieBas.getP360());
		address.put("480P", movieBas.getP480());
		address.put("720P", movieBas.getP720());

/*		
		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
		
		ATSCover allCovers = new ATSCover();
		ATSCover allJCovers = new ATSCover();
		
		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
				allCovers = ATSCover.builder()
						.horizontal_large(movieCoverImage.getHorizontalLarge())
						.horizontal_small(movieCoverImage.getHorizontalSmall())
						.vertical_large(movieCoverImage.getVerticalLarge())
						.vertical_small(movieCoverImage.getVerticalSmall())
						.build();
			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
				allJCovers = ATSCover.builder()
        						.horizontal_large(movieCoverImage.getHorizontalLarge())
        						.horizontal_small(movieCoverImage.getHorizontalSmall())
        						.vertical_large(movieCoverImage.getVerticalLarge())
        						.vertical_small(movieCoverImage.getVerticalSmall())
        						.build();
			}
		}
*/
		ATSCover allCovers = commonService.getCover(movieBas.getMovId(), langCd);
		List<ATSMovieTag> tags = commonService.getRelTagList(movieBas.getMovId(), langCd);
		
		ATSMovieBrowse actorMovie = ATSMovieBrowse.builder()
												  .actor("")											// TODO : 어디서 가져오는 지 확인 피료
												  .actorList(actorList)
							    				  .address(address)
							    				  .allCovers(allCovers)
//							    				  .allJCovers(allJCovers)
							    				  
							    				  .attenStatus(0)										// TODO : 어디서 가져오는 지 확인 피료
							    				  .browId("1")											// TODO : 어디서 가져오는 지 확인 피료
							    				  .browToken("")										// TODO : 어디서 가져오는 지 확인 피료
//							    				  .cover("/1.webp")
							    				  .director("")											// TODO : 어디서 가져오는 지 확인 피료
							    				  .domain(movieBas.getDomain())
							    				  .encryKey("612400680")								// TODO : 어디서 가져오는 지 확인 피료
							    				  .gmtCreate(movieBas.getGmtCreate().toLocalDate())
							    				  .hasDiss(movieBas.isHasDiss())
							    				  .hasDown(movieBas.isHasDown())
							    				  .hasFavor(movieBas.isHasLove())
							    				  .hasUp(movieBas.isHasUp())
							    				  .id(movieBas.getMovId())
							    				  .isMosaic(movieBas.isMosaic())
							    				  .loveCnt(movieBas.getLoveCnt())
							    				  .mins(movieBas.getMins())
							    				  
							    				  .movCls(2)											// TODO : 어디서 가져오는 지 확인 피료
							    				  
							    				  .movDesc(movieBas.getMovDesc())
							    				  .movName(movieBas.getMovName())
							    				  .movScore(movieBas.getMovScore()+"")
//							    				  .movSize(null)										// TODO : 어디서 가져오는 지 확인 피료
							    				  .movSn(movieBas.getMovSn())
							    				  .movSnOri(movieBas.getMovSnOri())
							    				  .originalMovid(-1)									// TODO : 어디서 가져오는 지 확인 피료
							    				  .p2pSharpness("")										// TODO : 어디서 가져오는 지 확인 피료
							    				  .p2pToken("")											// TODO : 어디서 가져오는 지 확인 피료
							    				  .playCnt(movieBas.getPlayCnt())
							    				  .remainPlayCnt(0)										// TODO : 어디서 가져오는 지 확인 피료
							    				  .strPlayCnt(movieBas.getPlayCntStr())
							    				  .tags(tags)
//							    				  .uploadUserFans(5774)									// 피료업슴
//							    				  .uploadUserIcon("uploadericon/1.jpg")					// 피료업슴
//							    				  .uploadUserName("?角遇到怪")							// 피료업슴
//							    				  .uploaderInviteCode("S580019")						// 아직 피료업슴
							    				  .userId(movieBas.getGmtCreateId())
							    				  .vipFlag(0)											// TODO : 어디서 가져오는 지 확인 피료
							    				  .ydaPlayCnt(1)										// TODO : 어디서 가져오는 지 확인 피료
							    				  .build();
		returnList.add(actorMovie);
    	return returnList;
    }
    
    
    
    /**
     * 영상 목록 조회 : 조건 검색  : 영상의 태그가 비슷한 거 핫 (좋아요 많은 순위 10개)
     * @param userId
     * @return
     */
    public List<ATSMovie> getSimilarMovieList(HashMap<String,Object> map, ATSLangCode langCd) {
    	List<ATSMovie> returnList = new ArrayList<ATSMovie>();
    	
    	map.put("pageSize", 10);
    	List<ATSMovieBas> movieList = movieRepository.selectSimilarMovieList(map);
    	
    	for(ATSMovieBas movieBas:movieList) {
    		
    		HashMap<String,String> address = new HashMap<String,String>();
    		address.put("240P", movieBas.getP240());
    		address.put("360P", movieBas.getP360());
    		address.put("480P", movieBas.getP480());
    		address.put("720P", movieBas.getP720());
    		address.put("1080P", movieBas.getP1080());
 
/*    		
    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
    		
    		ATSCover allCovers = new ATSCover();
    		ATSCover allJCovers = new ATSCover();
    		
    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
    			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
    				allCovers = ATSCover.builder()
    							.horizontal_large(movieCoverImage.getHorizontalLarge())
    							.horizontal_small(movieCoverImage.getHorizontalSmall())
    							.vertical_large(movieCoverImage.getVerticalLarge())
    							.vertical_small(movieCoverImage.getVerticalSmall())
    						    .build();
    			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
    				allJCovers = ATSCover.builder()
							.horizontal_large(movieCoverImage.getHorizontalLarge())
							.horizontal_small(movieCoverImage.getHorizontalSmall())
							.vertical_large(movieCoverImage.getVerticalLarge())
							.vertical_small(movieCoverImage.getVerticalSmall())
						    .build();
    			}
    		}
*/
    		ATSCover allCovers = commonService.getCover(movieBas.getMovId(), langCd);
    		List<ATSMovieTag> relTagName = commonService.getRelTagList(movieBas.getMovId(), langCd);
   		
    		ATSMovie movie = ATSMovie.builder()
    							     .address(address)
    							     .allCovers(allCovers)
//    							     .allJCovers(allJCovers)
//    							     .cover("/5.webp")						// TODO : 어디서 가져오는 지 확인 피료
    							     .id(movieBas.getMovId())
//    							     .jThumbnail("/5.jpg")					// 삭제
    							     .mins(movieBas.getMins())
    							     .movName(movieBas.getMovName())
    							     .movScore(movieBas.getMovScore()+"")
    							     .movSize(null)							// TODO : 어디서 가져오는 지 확인 피료
    							     .movSn(movieBas.getMovSn())
    							     .movSnOri(movieBas.getMovSnOri())
    							     .playCnt(movieBas.getPlayCnt())
//    							     .primevalCover("/1.jpg")				// 삭제
    							     .relTagName(relTagName)				// TODO : 어디서 가져오는 지 확인 피료
    							     .score(0)								// TODO : 어디서 가져오는 지 확인 피료
    							     .selector("")							// TODO : 어디서 가져오는 지 확인 피료
    							     .strPlayCnt(movieBas.getPlayCntStr())
//    							     .thumbnail("/5.jpg")						// TODO : 어디서 가져오는 지 확인 피료
    							     .userId(movieBas.getGmtCreateId())			// 생성자 아이디
    							     .vipFlag(0)								// TODO : 어디서 가져오는 지 확인 피료
    							     .build();
    		
    		returnList.add(movie);
    	}
    	
        return returnList;
    }
    
    @Transactional
    public void setMovieUp(HashMap<String,Object> map, String userId) {
    	ATSMovieReactionTxn movieReaction = ATSMovieReactionTxn.builder()
    														   .movId((int) map.get("movId"))
    														   .userId(userId)
    														   .reactionType(ATSReactionType.up)
    														   .build();
    	
    	movieRepository.insertMovieReactionTxnUp(movieReaction);
    	movieRepository.updateMovieBasUp(movieReaction.getMovId());
    }
    
    @Transactional
    public void setMovieDown(HashMap<String,Object> map, String userId) {
    	ATSMovieReactionTxn movieReaction = ATSMovieReactionTxn.builder()
															   .movId((int) map.get("movId"))
															   .userId(userId)
															   .reactionType(ATSReactionType.down)
															   .build();
    	movieRepository.insertMovieReactionTxnDown(movieReaction);
    	movieRepository.updateMovieBasDown(movieReaction.getMovId());
    }
}
