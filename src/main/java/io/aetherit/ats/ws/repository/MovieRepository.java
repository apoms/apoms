package io.aetherit.ats.ws.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.dao.ATSMovieActorBas;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieCoverImage;
import io.aetherit.ats.ws.model.dao.ATSMovieHst;
import io.aetherit.ats.ws.model.dao.ATSMovieReactionTxn;
import io.aetherit.ats.ws.model.dao.ATSMovieTagDtl;
import io.aetherit.ats.ws.repository.mapper.MovieMapper;

@Repository
public class MovieRepository {
    private MovieMapper movieMapper;

    @Autowired
    public MovieRepository(MovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }

    public List<ATSMovieBas> selectNewMovieList() {
        return movieMapper.selectNewMovieList();
    }
    
    public List<ATSMovieTagDtl> selectMovieTagDtlList(HashMap<String,Object> map) {
    	return movieMapper.selectMovieTagDtlList(map);
    }
    
    public List<ATSMovieCoverImage> selectCoverImageList(HashMap<String,Object> map) {
    	return movieMapper.selectCoverImageList(map);
    }
    
    public List<ATSMovieActorBas> selectRecommendActorList(HashMap<String,Object> map) {
    	return movieMapper.selectRecommendActorList(map);
    }
    
    public List<ATSMovieBas> selectActorMovieList(HashMap<String,Object> map) {
    	return movieMapper.selectActorMovieList(map);
    }
    
    public int selectActorMovieTotalCount(HashMap<String,Object> map) {
    	return movieMapper.selectActorMovieTotalCount(map);
    }
    
    public List<ATSMovieBas> selectTodayRankingList(HashMap<String,Object> map) {
    	return movieMapper.selectTodayRankingList(map);
    }
    
    public List<ATSMovieBas> selectTodayRecommandList(HashMap<String,Object> map) {
    	return movieMapper.selectTodayRecommandList(map);
    }
    
    public List<ATSMovieBas> selectWonderfulMovieList(HashMap<String,Object> map) {
    	return movieMapper.selectWonderfulMovieList(map);
    }
    
    public List<ATSMovieBas> selectSelectionMovieList(HashMap<String,Object> map) {
    	return movieMapper.selectSelectionMovieList(map);
    }
    
    public List<ATSMovieActorBas> selectNewTopActorList(HashMap<String,Object> map) {
    	return movieMapper.selectNewTopActorList(map);
    }
    
    
    public List<ATSMovieBas> selectMovieList(HashMap<String,Object> map) {
    	return movieMapper.selectMovieList(map);
    }
    
    public int selectMovieTotalCount(HashMap<String,Object> map) {
    	return movieMapper.selectMovieTotalCount(map);
    }
    
    
    public List<ATSMovieBas> selectSearchMovieList(HashMap<String,Object> map) {
    	return movieMapper.selectSearchMovieList(map);
    }
    
    public int selectSearchMovieTotalCount(HashMap<String,Object> map) {
    	return movieMapper.selectSearchMovieTotalCount(map);
    }
    
    public ATSMovieActorBas selectActorDetail(HashMap<String,Object> map) {
    	return movieMapper.selectActorDetail(map);
    }
    
    public List<ATSMovieActorBas> selectMovieActorList(HashMap<String,Object> map) {
    	return movieMapper.selectMovieActorList(map);
    }
    
    public ATSMovieBas selectMovieBas(HashMap<String,Object> map) {
    	return movieMapper.selectMovieBas(map);
    }
    
    public List<ATSMovieBas> selectSimilarMovieList(HashMap<String,Object> map) {
    	return movieMapper.selectSimilarMovieList(map);
    }
    
    public int insertMovieReactionTxnUp(ATSMovieReactionTxn movieReaction) {
    	return movieMapper.insertMovieReactionTxnUp(movieReaction);
    }
    
    public int updateMovieBasUp(int movId) {
    	return movieMapper.updateMovieBasUp(movId);
    }
    
    public int insertMovieReactionTxnDown(ATSMovieReactionTxn movieReaction) {
    	return movieMapper.insertMovieReactionTxnDown(movieReaction);
    }
    
    public int updateMovieBasDown(int movId) {
    	return movieMapper.updateMovieBasDown(movId);
    }
    
    public int insertMovieHst(ATSMovieHst movieHst) {
    	return movieMapper.insertMovieHst(movieHst);
    }
    
}



