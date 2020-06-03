package io.aetherit.ats.ws.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.dao.ATSMovieActorBas;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieSearchTxn;
import io.aetherit.ats.ws.repository.mapper.RankingMapper;

@Repository
public class RankingRepository {
    private RankingMapper rankingMapper;

    @Autowired
    public RankingRepository(RankingMapper rankingMapper) {
        this.rankingMapper = rankingMapper;
    }
    
    public List<ATSMovieBas> selectViewRankingList(HashMap<String,Object> map) {
    	return rankingMapper.selectViewRankingList(map);
    }
    
    public int selectRankingViewMovieTotalCount(HashMap<String,Object> map) {
    	return rankingMapper.selectRankingViewMovieTotalCount(map);
    }
    
    public List<ATSMovieBas> selectNewRankingList(HashMap<String,Object> map) {
    	return rankingMapper.selectNewRankingList(map);
    }
    
    public int selectRankingNewMovieTotalCount(HashMap<String,Object> map) {
    	return rankingMapper.selectRankingNewMovieTotalCount(map);
    }
    
    public List<ATSMovieBas> selectLusirRankingList(HashMap<String,Object> map) {
    	return rankingMapper.selectLusirRankingList(map);
    }
    
    public int selectRankingLusirMovieTotalCount(HashMap<String,Object> map) {
    	return rankingMapper.selectRankingLusirMovieTotalCount(map);
    }
    
    public List<ATSMovieBas> selectTucaoRankingList(HashMap<String,Object> map) {
    	return rankingMapper.selectTucaoRankingList(map);
    }
    
    public int selectRankingTucaoMovieTotalCount(HashMap<String,Object> map) {
    	return rankingMapper.selectRankingTucaoMovieTotalCount(map);
    }
    
    public List<ATSMovieActorBas> selectRankingActorList(HashMap<String,Object> map) {
    	return rankingMapper.selectRankingActorList(map);
    }
    
    public int selectRankingActorTotalCount(HashMap<String,Object> map) {
    	return rankingMapper.selectRankingActorTotalCount(map);
    }
    
    public List<ATSMovieSearchTxn> selectSearchHotList(HashMap<String,Object> map) {
    	return rankingMapper.selectSearchHotList(map);
    }
}



