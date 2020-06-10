package io.aetherit.ats.ws.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.aetherit.ats.ws.model.common.ATSSelection;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieChannelCtg;
import io.aetherit.ats.ws.model.dao.ATSMovieCoverImage;
import io.aetherit.ats.ws.model.movie.ATSCover;
import io.aetherit.ats.ws.model.movie.ATSMovieTag;
import io.aetherit.ats.ws.model.movie.ATSNewMovie;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.repository.ChannelRepository;
import io.aetherit.ats.ws.repository.MovieRepository;

@Service
public class ChannelService {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ChannelService.class);

    private CommonService commonService;
    private ChannelRepository channelRepository;
    private MovieRepository movieRepository;

    @Autowired
    public ChannelService(ChannelRepository channelRepository, MovieRepository movieRepository, CommonService commonService) {
        this.channelRepository = channelRepository;
        this.movieRepository = movieRepository;
        this.commonService = commonService;
    }

    /**
     * 배너 목록 조회
     * @param userId
     * @return
     */
    public List<ATSMovieChannelCtg> getDefaultChannelList(ATSLangCode langCd) {
        return channelRepository.selectDefaultChannelList();
    }
    
    /**
     * 각 채널 의 최신작 4개씩
     * @return
     */
    public List<ATSSelection> getSelectionChannelList(ATSLangCode langCd) {
    	List<ATSSelection> selectionChannelList = new ArrayList<ATSSelection>();
    	
    	List<ATSMovieChannelCtg> subChannelList = channelRepository.selectSubChannelList();
    	
    	for(ATSMovieChannelCtg movieChannelCtg:subChannelList) {
    		
    		List<ATSNewMovie> movieList = new ArrayList<ATSNewMovie>();
    		
    		HashMap<String,Object> map = new HashMap<String,Object>();
    		map.put("moduleId", movieChannelCtg.getId());
    		map.put("limit", 4);
    		List<ATSMovieBas> selectionMovieList = movieRepository.selectSelectionMovieList(map);
    		
    		for(ATSMovieBas movieBas:selectionMovieList) {
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
        		
        		ATSNewMovie selectionMovie = ATSNewMovie.builder()
				        							    .address(address)
				        							    .allCovers(allCovers)
//				        							    .allJCovers(allJCovers)					// 삭제
//				        							    .cover("/5.webp")						// 삭제
				        							    .hasDownload(movieBas.isHasDown())
				        							    .hasFavor(movieBas.isHasLove())
				        							    .id(movieBas.getMovId())
//				        							    .jCover("/1.jpg")						// 삭제
//				        							    .jThumbnail("/2.jpg")					// 삭제
				        							    .mins(movieBas.getMins())
				        							    .movName(movieBas.getMovName())
				        							    .movScore(movieBas.getMovScore()+"")
//				        							    .movSize(null)							// 삭제
				        							    .movSnOri(movieBas.getMovSnOri())
				        							    .playCnt(movieBas.getPlayCnt())
				        							    .playCntStr(movieBas.getPlayCntStr())
				        							    .relTag(relTag)
//				        							    .thumbnail("/5.webp")					// 삭제
				        							    .userId(movieBas.getGmtCreateId())					// TODO : 어디서 가져오는 지 확인 피료
				        							    .vipFlag(0)
				        							    .build();
        		
        		movieList.add(selectionMovie);
        	}
    		
    		ATSSelection sellectioChannel = ATSSelection.builder()
    													.clsId(null)										// TODO : 어디서 가져오는 지 확인 피료
    													.id(Integer.parseInt(movieChannelCtg.getId()))
    													.movList(movieList)
    													.name(movieChannelCtg.getName())
    													.navId(1046)									// 채널 아이디 적용								// TODO : 어디서 가져오는 지 확인 피료
    													.selectionType(Integer.parseInt(movieChannelCtg.getShowType()))		// 노출타입
    													.build();
    		
    		selectionChannelList.add(sellectioChannel);
    	}
        return selectionChannelList;
    }
    
    /**
     * 채널 아이디에 해당하는 하위 모듈의 최신작 4개씩
     * @param moduleId
     * @return
     */
    public List<ATSSelection> getSelectionChannel2List(int moduleId, ATSLangCode langCd) {
    	List<ATSSelection> selectionChannelList = new ArrayList<ATSSelection>();
    	
    	HashMap<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("moduleId", moduleId);
    	
    	List<ATSMovieChannelCtg> subChannelList = channelRepository.selectSubChannel2List(paramMap);
    	
    	for(ATSMovieChannelCtg movieChannelCtg:subChannelList) {
    		
    		List<ATSNewMovie> movieList = new ArrayList<ATSNewMovie>();
    		
    		HashMap<String,Object> map = new HashMap<String,Object>();
    		map.put("moduleId", movieChannelCtg.getId());
    		map.put("limit", 4);
    		
    		/**
    		 *  기준
			 * 1. 신작(최신 업로드 순) 4개
    		 */
    		List<ATSMovieBas> selectionMovieList = movieRepository.selectSelectionMovieList(map);    
    		
    		for(ATSMovieBas movieBas:selectionMovieList) {
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
        		
    			ATSNewMovie selectionMovie = ATSNewMovie.builder()
								    					.address(address)
								    					.allCovers(allCovers)
//								    					.allJCovers(allJCovers)
//								    					.cover("/5.webp")			// TODO : 어디서 가져오는 지 확인 피료
								    					.hasDownload(movieBas.isHasDown())
								    					.hasFavor(movieBas.isHasLove())				// 컬럼 없음
								    					.id(movieBas.getMovId())
//								    					.jCover("/1.jpg")			// TODO : 어디서 가져오는 지 확인 피료
//								    					.jThumbnail("/2.jpg")		// TODO : 어디서 가져오는 지 확인 피료
								    					.mins(movieBas.getMins())
								    					.movName(movieBas.getMovName())
								    					.movScore(movieBas.getMovScore()+"")
//								    					.movSize(null)				// TODO : 어디서 가져오는 지 확인 피료
								    					.movSnOri(movieBas.getMovSnOri())
								    					.playCnt(movieBas.getPlayCnt())
								    					.playCntStr(movieBas.getPlayCntStr())
								    					.relTag(relTag)
//								    					.thumbnail("/5.webp")		// TODO : 어디서 가져오는 지 확인 피료
								    					.userId(1)					// TODO : 어디서 가져오는 지 확인 피료
								    					.vipFlag(0)
								    					.build();
    			
    			movieList.add(selectionMovie);
    		}
    		
    		ATSSelection sellectioChannel = ATSSelection.builder()
    				.clsId(null)										// TODO : 어디서 가져오는 지 확인 피료
    				.id(Integer.parseInt(movieChannelCtg.getId()))
    				.movList(movieList)
    				.name(movieChannelCtg.getName())
    				.navId(1046)										// TODO : 어디서 가져오는 지 확인 피료
    				.selectionType(Integer.parseInt(movieChannelCtg.getChannelType()))		// TODO : 어디서 가져오는 지 확인 피료
    				.build();
    		
    		selectionChannelList.add(sellectioChannel);
    	}
    	return selectionChannelList;
    }
    
    
    
}
