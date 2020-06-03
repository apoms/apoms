package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSMovieActorBas;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieSearchTxn;

public interface RankingMapper {
	public List<ATSMovieBas> selectViewRankingList(HashMap<String,Object> map);
	public int selectRankingViewMovieTotalCount(HashMap<String,Object> map);
	
	public List<ATSMovieBas> selectNewRankingList(HashMap<String,Object> map);
	public int selectRankingNewMovieTotalCount(HashMap<String,Object> map);
	
	public List<ATSMovieBas> selectLusirRankingList(HashMap<String,Object> map);
	public int selectRankingLusirMovieTotalCount(HashMap<String,Object> map);
	
	public List<ATSMovieBas> selectTucaoRankingList(HashMap<String,Object> map);
	public int selectRankingTucaoMovieTotalCount(HashMap<String,Object> map);
	
	public List<ATSMovieActorBas> selectRankingActorList(HashMap<String,Object> map);
	public int selectRankingActorTotalCount(HashMap<String,Object> map);
	
	public List<ATSMovieSearchTxn> selectSearchHotList(HashMap<String,Object> map);
	
}
