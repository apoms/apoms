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
import io.aetherit.ats.ws.model.dao.ATSMovieTagDtl;
import io.aetherit.ats.ws.model.movie.ATSCover;
import io.aetherit.ats.ws.model.movie.ATSMovieTag;
import io.aetherit.ats.ws.model.movie.ATSNewMovie;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.repository.ChannelRepository;
import io.aetherit.ats.ws.repository.MovieRepository;

@Service
public class CommonService {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

    private ChannelRepository channelRepository;
    private MovieRepository movieRepository;

    @Autowired
    public CommonService(ChannelRepository channelRepository,MovieRepository movieRepository) {
        this.channelRepository = channelRepository;
        this.movieRepository = movieRepository;
    }

    
    public ATSCover getCover(int movId, ATSLangCode langCd) {
    	HashMap<String,Object> coverMap = new HashMap<String,Object>();
		coverMap.put("movId", movId);
		coverMap.put("langCd", langCd);
		
		List<ATSMovieCoverImage> coverImageList = movieRepository.selectCoverImageList(coverMap);
		
		ATSCover cover = new ATSCover();
		
		for(ATSMovieCoverImage movieCoverImage:coverImageList) {
			if(movieCoverImage.getLangCd()==langCd) {
				cover = ATSCover.builder()
							    .horizontal_large(movieCoverImage.getHorizontalLarge())
							    .horizontal_small(movieCoverImage.getHorizontalSmall())
							    .vertical_large(movieCoverImage.getVerticalLarge())
							    .vertical_small(movieCoverImage.getVerticalSmall())
							    .build();
			}
		}
		return cover;
    }
    
    
    public List<ATSMovieTag> getRelTagList(int movId, ATSLangCode langCd) {
    	HashMap<String,Object> coverMap = new HashMap<String,Object>();
		coverMap.put("movId", movId);
		coverMap.put("langCd", langCd);
		
    	List<ATSMovieTagDtl> movieTagDtlList = movieRepository.selectMovieTagDtlList(coverMap);
    	List<ATSMovieTag> relTagList = new ArrayList<ATSMovieTag>();
    	
    	for(ATSMovieTagDtl movieTagDtl : movieTagDtlList){
    		ATSMovieTag relTag = ATSMovieTag.builder()
    										.id(movieTagDtl.getId())
    										.name(movieTagDtl.getName())
    										.build();
    		relTagList.add(relTag);
    	}
    	return relTagList;
    }
    
    
    
}
