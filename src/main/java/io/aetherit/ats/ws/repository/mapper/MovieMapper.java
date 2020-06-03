package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSMovieActorBas;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieCoverImage;
import io.aetherit.ats.ws.model.dao.ATSMovieReactionTxn;
import io.aetherit.ats.ws.model.dao.ATSMovieTagDtl;

public interface MovieMapper {
	public List<ATSMovieBas> selectNewMovieList();
	public List<ATSMovieTagDtl> selectMovieTagDtlList(HashMap<String,Object> map) ;
	public List<ATSMovieCoverImage> selectCoverImageList(HashMap<String,Object> map);
	public List<ATSMovieActorBas> selectRecommendActorList(HashMap<String,Object> paraMap);
	
	public List<ATSMovieBas> selectActorMovieList(HashMap<String,Object> map);
	public int selectActorMovieTotalCount(HashMap<String,Object> map);
	
	public List<ATSMovieBas> selectTodayRankingList(HashMap<String,Object> map);
	public List<ATSMovieBas> selectTodayRecommandList(HashMap<String,Object> map);
	public List<ATSMovieBas> selectWonderfulMovieList(HashMap<String,Object> map);
	public List<ATSMovieBas> selectSelectionMovieList(HashMap<String,Object> map);
	public List<ATSMovieActorBas> selectNewTopActorList(HashMap<String,Object> map);
	
	public List<ATSMovieBas> selectMovieList(HashMap<String,Object> map);
	public int selectMovieTotalCount(HashMap<String,Object> map);
	
	public List<ATSMovieBas> selectSearchMovieList(HashMap<String,Object> map);
	public int selectSearchMovieTotalCount(HashMap<String,Object> map);
	
	public ATSMovieActorBas selectActorDetail(HashMap<String,Object> map);
	public List<ATSMovieActorBas> selectMovieActorList(HashMap<String,Object> map);
	public ATSMovieBas selectMovieBas(HashMap<String,Object> map);
	public List<ATSMovieBas> selectSimilarMovieList(HashMap<String,Object> map);
	
	int insertMovieReactionTxnUp(ATSMovieReactionTxn movieReaction);
	int updateMovieBasUp(int movId);
	int insertMovieReactionTxnDown(ATSMovieReactionTxn movieReaction);
	int updateMovieBasDown(int movId);
}
