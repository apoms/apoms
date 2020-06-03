package io.aetherit.ats.ws.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.aetherit.ats.ws.model.actor.ATSActorRanking;
import io.aetherit.ats.ws.model.dao.ATSMovieActorBas;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieSearchTxn;
import io.aetherit.ats.ws.model.movie.ATSCover;
import io.aetherit.ats.ws.model.movie.ATSMovieSearchHot;
import io.aetherit.ats.ws.model.movie.ATSRanking;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.repository.RankingRepository;

@Service
public class RankingService {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RankingService.class);

    private CommonService commonService;
    private RankingRepository rankingRepository;

    @Autowired
    public RankingService(RankingRepository rankingRepository, CommonService commonService) {
        this.rankingRepository = rankingRepository;
        this.commonService = commonService;
    }

    
    /**
	 * viewRankingdMov Set : 기준 기간별 view count가 있는 영상 중 30개만
	 */
	public List<ATSRanking> getRankingViewMovieList(HashMap<String,Object> map, ATSLangCode langCd) {
        List<ATSRanking> rankingViewMovieList  = new ArrayList<ATSRanking>();
        
        /**
         * 노출 수 상위 30개
         */
		List<ATSMovieBas> rankingList = rankingRepository.selectViewRankingList(map);
		
		for(ATSMovieBas movieBas:rankingList) {
			HashMap<String,String> address = new HashMap<String,String>();
    		address.put("480P", movieBas.getP480());

/*    		
    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
    		
    		ATSCover url = new ATSCover();
    		ATSCover oriUrl = new ATSCover();
    		
    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
    			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
    				url = ATSCover.builder()
						  .horizontal_large(movieCoverImage.getHorizontalLarge())
						  .horizontal_small(movieCoverImage.getHorizontalSmall())
						  .vertical_large(movieCoverImage.getVerticalLarge())
						  .vertical_small(movieCoverImage.getVerticalSmall())
						  .build();
    			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
    				oriUrl = ATSCover.builder()
							 .horizontal_large(movieCoverImage.getHorizontalLarge())
							 .horizontal_small(movieCoverImage.getHorizontalSmall())
							 .vertical_large(movieCoverImage.getVerticalLarge())
							 .vertical_small(movieCoverImage.getVerticalSmall())
							 .build();
    			}
    		}
*/
    		ATSCover url = commonService.getCover(movieBas.getMovId(), langCd);
    		
    		ATSRanking viewRanking = ATSRanking.builder()
							    				.address(address)
							    				.arrowState(1)											// TODO : 어디서 가져오는 지 확인 피료
							    				.collectNumber(movieBas.getPlayCnt())
							    				.downloadNumber(movieBas.getDownCnt())
							    				.mins(movieBas.getMins())
							    				.movId(movieBas.getMovId())
//							    				.movSize(null)											// 삭제
							    				.mov_index(1)											// TODO : 어디서 가져오는 지 확인 피료
							    				.name(movieBas.getMovName())
//							    				.oriUrl(oriUrl)
							    				.playNumber(movieBas.getPlayCnt())
							    				.ranking(1)												// 랭킹 순위는 순서대로 1 ~ 30까지
							    				.release_time(movieBas.getMins().getMinute())
							    				.type(movieBas.getMovType())							// 영상타입
							    				.url(url)
							    				.vipFlag(0)
							    				.build();
    		rankingViewMovieList.add(viewRanking);
		}
    	return rankingViewMovieList;
    }
	
	/**
	 * viewRankingdMov Set : 기준 기간별 view count가 있는 영상 전체 갯수
	 */
	public int getRankingViewMovieTotalCount(HashMap<String,Object> map) {
		return rankingRepository.selectRankingViewMovieTotalCount(map);
	}
	
	
	/**
	 * New RankingdMov Set   : 출시일 기준 한달 이내의 영상에서 추출
	 */
	public List<ATSRanking> getRankingNewMovieList(HashMap<String,Object> map, ATSLangCode langCd) {
        List<ATSRanking> rankingNewMovieList  = new ArrayList<ATSRanking>();
		List<ATSMovieBas> rankingList = rankingRepository.selectNewRankingList(map);
		
		for(ATSMovieBas movieBas:rankingList) {
			HashMap<String,String> address = new HashMap<String,String>();
    		address.put("480P", movieBas.getP480());
/*    		
    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
    		
    		ATSCover url = new ATSCover();
    		ATSCover oriUrl = new ATSCover();
    		
    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
    			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
    				url = ATSCover.builder()
						  .horizontal_large(movieCoverImage.getHorizontalLarge())
						  .horizontal_small(movieCoverImage.getHorizontalSmall())
						  .vertical_large(movieCoverImage.getVerticalLarge())
						  .vertical_small(movieCoverImage.getVerticalSmall())
						  .build();
    			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
    				oriUrl = ATSCover.builder()
							 .horizontal_large(movieCoverImage.getHorizontalLarge())
							 .horizontal_small(movieCoverImage.getHorizontalSmall())
							 .vertical_large(movieCoverImage.getVerticalLarge())
							 .vertical_small(movieCoverImage.getVerticalSmall())
							 .build();
    			}
    		}
*/
    		ATSCover url = commonService.getCover(movieBas.getMovId(), langCd);
    		
    		ATSRanking newRanking = ATSRanking.builder()
							    				.address(address)
							    				.arrowState(1)									// TODO : 어디서 가져오는 지 확인 피료
							    				.collectNumber(movieBas.getPlayCnt())
							    				.downloadNumber(movieBas.getDownCnt())
							    				.mins(movieBas.getMins())
							    				.movId(movieBas.getMovId())
//							    				.movSize(null)									// TODO : 어디서 가져오는 지 확인 피료
							    				.mov_index(1)									// TODO : 어디서 가져오는 지 확인 피료
							    				.name(movieBas.getMovName())
//							    				.oriUrl(oriUrl)
							    				.playNumber(movieBas.getPlayCnt())
							    				.ranking(1)										// 이건 어떻게 해야 하지?
							    				.release_time(movieBas.getMins().getMinute())
							    				.type(movieBas.getMovType())					// TODO : 어디서 가져오는 지 확인 피료
							    				.url(url)
							    				.vipFlag(0)
							    				.build();
    		rankingNewMovieList.add(newRanking);
		}
    	return rankingNewMovieList;
    }
	
	/**
	 *  New RankingdMov Count : 기준 기간별 UP count가 있는 영상 전체 갯수
	 */
	public int getRankingNewMovieTotalCount(HashMap<String,Object> map) {
		return rankingRepository.selectRankingNewMovieTotalCount(map);
	}
	
	
	/**
	 * UpRankingdMov Set : 기준 기간별 UP count가 있는 영상 전체 갯수
	 */
	public List<ATSRanking> getRankingLusirMovieList(HashMap<String,Object> map, ATSLangCode langCd) {
        List<ATSRanking> rankingLusirMovieList  = new ArrayList<ATSRanking>();
		List<ATSMovieBas> rankingList = rankingRepository.selectLusirRankingList(map);
		
		for(ATSMovieBas movieBas:rankingList) {
			HashMap<String,String> address = new HashMap<String,String>();
    		address.put("480P", movieBas.getP480());
/*    		
    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
    		
    		ATSCover url = new ATSCover();
    		ATSCover oriUrl = new ATSCover();
    		
    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
    			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
    				url = ATSCover.builder()
						  .horizontal_large(movieCoverImage.getHorizontalLarge())
						  .horizontal_small(movieCoverImage.getHorizontalSmall())
						  .vertical_large(movieCoverImage.getVerticalLarge())
						  .vertical_small(movieCoverImage.getVerticalSmall())
						  .build();
    			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
    				oriUrl = ATSCover.builder()
							 .horizontal_large(movieCoverImage.getHorizontalLarge())
							 .horizontal_small(movieCoverImage.getHorizontalSmall())
							 .vertical_large(movieCoverImage.getVerticalLarge())
							 .vertical_small(movieCoverImage.getVerticalSmall())
							 .build();
    			}
    		}
*/
    		
    		ATSCover url = commonService.getCover(movieBas.getMovId(), langCd);
    		
    		ATSRanking lusirRanking = ATSRanking.builder()
							    				.address(address)
							    				.arrowState(1)				// TODO : 어디서 가져오는 지 확인 피료
							    				.collectNumber(movieBas.getPlayCnt())
							    				.downloadNumber(movieBas.getDownCnt())
							    				.mins(movieBas.getMins())
							    				.movId(movieBas.getMovId())
//							    				.movSize(null)				// TODO : 어디서 가져오는 지 확인 피료
							    				.mov_index(1)				// TODO : 어디서 가져오는 지 확인 피료
							    				.name(movieBas.getMovName())
//							    				.oriUrl(oriUrl)
							    				.playNumber(movieBas.getPlayCnt())
							    				.ranking(1)					// 이건 어떻게 해야 하지?
							    				.release_time(movieBas.getMins().getMinute())
							    				.type(movieBas.getMovType())					// TODO : 어디서 가져오는 지 확인 피료
							    				.url(url)
							    				.vipFlag(0)
							    				.build();
    		rankingLusirMovieList.add(lusirRanking);
		}
    	return rankingLusirMovieList;
    }
	
	/**
	 * viewRankingdMov Set
	 */
	public int getRankingLusirMovieTotalCount(HashMap<String,Object> map) {
		return rankingRepository.selectRankingLusirMovieTotalCount(map);
	}
	
	
	/**
	 * 평점순 RankingdMov Set : upCnt/(upCnt+dissCnt) 평점순서
	 */
	public List<ATSRanking> getRankingTucaoMovieList(HashMap<String,Object> map, ATSLangCode langCd) {
        List<ATSRanking> rankingTucaoMovieList  = new ArrayList<ATSRanking>();
		List<ATSMovieBas> rankingList = rankingRepository.selectTucaoRankingList(map);
		
		for(ATSMovieBas movieBas:rankingList) {
			HashMap<String,String> address = new HashMap<String,String>();
    		address.put("480P", movieBas.getP480());

/*    		
    		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(movieBas.getMovId());
    		
    		ATSCover url = new ATSCover();
    		ATSCover oriUrl = new ATSCover();
    		
    		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
    			if(movieCoverImage.getLangCd()==ATSLangCode.chn) {
    				url = ATSCover.builder()
						  .horizontal_large(movieCoverImage.getHorizontalLarge())
						  .horizontal_small(movieCoverImage.getHorizontalSmall())
						  .vertical_large(movieCoverImage.getVerticalLarge())
						  .vertical_small(movieCoverImage.getVerticalSmall())
						  .build();
    			}else if(movieCoverImage.getLangCd()==ATSLangCode.jpn) {
    				oriUrl = ATSCover.builder()
							 .horizontal_large(movieCoverImage.getHorizontalLarge())
							 .horizontal_small(movieCoverImage.getHorizontalSmall())
							 .vertical_large(movieCoverImage.getVerticalLarge())
							 .vertical_small(movieCoverImage.getVerticalSmall())
							 .build();
    			}
    		}
*/
    		ATSCover url = commonService.getCover(movieBas.getMovId(), langCd);
    		
    		ATSRanking TucaoRanking = ATSRanking.builder()
							    				.address(address)
							    				.arrowState(1)				// TODO : 어디서 가져오는 지 확인 피료
							    				.collectNumber(movieBas.getPlayCnt())
							    				.downloadNumber(movieBas.getDownCnt())
							    				.mins(movieBas.getMins())
							    				.movId(movieBas.getMovId())
//							    				.movSize(null)				// TODO : 어디서 가져오는 지 확인 피료
							    				.mov_index(1)				// TODO : 어디서 가져오는 지 확인 피료
							    				.name(movieBas.getMovName())
//							    				.oriUrl(oriUrl)
							    				.playNumber(movieBas.getPlayCnt())
							    				.ranking(1)					// 이건 어떻게 해야 하지?
							    				.release_time(movieBas.getMins().getMinute())
							    				.type(movieBas.getMovType())					// TODO : 어디서 가져오는 지 확인 피료
							    				.url(url)
							    				.vipFlag(0)
							    				.build();
    		rankingTucaoMovieList.add(TucaoRanking);
		}
    	return rankingTucaoMovieList;
    }
	
	/**
	 * viewRankingdMov Set
	 */
	public int getRankingTucaoMovieTotalCount(HashMap<String,Object> map) {
		return rankingRepository.selectRankingTucaoMovieTotalCount(map);
	}
	
	/**
	 * 출연작 기준 기간별  좋아용 순서대로 
	 * @param map
	 * @return
	 */
	public List<ATSActorRanking> getRankingActorList(HashMap<String,Object> map, ATSLangCode langCd) {
		List<ATSActorRanking> actorRankingList = new ArrayList<ATSActorRanking>();
		
        List<ATSMovieActorBas> actors = rankingRepository.selectRankingActorList(map);
        int i=0;
        for(ATSMovieActorBas actor:actors) {
        	ATSActorRanking actorRanking = ATSActorRanking.builder()
        												  .actorsId(actor.getActorIdx())
        												  .arrowState(2)							// TODO : 어디서 가져오는 지 확인 피료
        												  .movNumber(1114)							// TODO : 어디서 가져오는 지 확인 피료
        												  .name(actor.getActorName())
        												  .photoUrl(actor.getPhotoUrl())
        												  .ranking(i++)								// 
        												  .starLevel(actor.getStarLevel()+"")		// 총 출연작의 평점 
        												  .type((int) map.get("type"))									// TODO : 어디서 가져오는 지 확인 피료
        												  .build();
        	actorRankingList.add(actorRanking);
        }
		
		return actorRankingList;
	}
	
	public int getRankingActorTotalCount(HashMap<String,Object> map) {
		return rankingRepository.selectRankingActorTotalCount(map);
	}
	
	
	/**
	 * 인기 검색어 순위 10개 리스트
	 * @param map
	 * @return
	 */
	public List<ATSMovieSearchHot> getSearchHotList(HashMap<String,Object> map) {
		List<ATSMovieSearchHot> searchHotList = new ArrayList<ATSMovieSearchHot>();
		
        List<ATSMovieSearchTxn> searchList = rankingRepository.selectSearchHotList(map);
        int i=0;
        for(ATSMovieSearchTxn search:searchList) {
        	ATSMovieSearchHot searchHot = ATSMovieSearchHot.builder()
        												   .srchWord(search.getSrchWord())
        												   .build();
        	searchHotList.add(searchHot);
        }
		return searchHotList;
	}
}
