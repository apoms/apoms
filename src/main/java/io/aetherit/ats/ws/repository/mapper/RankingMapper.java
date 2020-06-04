package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSMovieActorBas;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieSearchTxn;

public interface RankingMapper {
	List<ATSMovieBas> selectViewRankingList(HashMap<String,Object> map);
	int selectRankingViewMovieTotalCount(HashMap<String,Object> map);
	
	List<ATSMovieBas> selectNewRankingList(HashMap<String,Object> map);
	int selectRankingNewMovieTotalCount(HashMap<String,Object> map);
	
	List<ATSMovieBas> selectLusirRankingList(HashMap<String,Object> map);
	int selectRankingLusirMovieTotalCount(HashMap<String,Object> map);
	
	List<ATSMovieBas> selectTucaoRankingList(HashMap<String,Object> map);
	int selectRankingTucaoMovieTotalCount(HashMap<String,Object> map);
	
	List<ATSMovieActorBas> selectRankingActorList(HashMap<String,Object> map);
	int selectRankingActorTotalCount(HashMap<String,Object> map);
	
	List<ATSMovieSearchTxn> selectSearchHotList(HashMap<String,Object> map);
	
}
