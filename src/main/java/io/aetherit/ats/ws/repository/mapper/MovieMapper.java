package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSMovieActorBas;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.model.dao.ATSMovieCoverImage;
import io.aetherit.ats.ws.model.dao.ATSMovieReactionTxn;
import io.aetherit.ats.ws.model.dao.ATSMovieTagDtl;

public interface MovieMapper {
	List<ATSMovieBas> selectNewMovieList();
	List<ATSMovieTagDtl> selectMovieTagDtlList(HashMap<String,Object> map) ;
	List<ATSMovieCoverImage> selectCoverImageList(HashMap<String,Object> map);
	List<ATSMovieActorBas> selectRecommendActorList(HashMap<String,Object> paraMap);
	
	List<ATSMovieBas> selectActorMovieList(HashMap<String,Object> map);
	int selectActorMovieTotalCount(HashMap<String,Object> map);
	
	List<ATSMovieBas> selectTodayRankingList(HashMap<String,Object> map);
	List<ATSMovieBas> selectTodayRecommandList(HashMap<String,Object> map);
	List<ATSMovieBas> selectWonderfulMovieList(HashMap<String,Object> map);
	List<ATSMovieBas> selectSelectionMovieList(HashMap<String,Object> map);
	List<ATSMovieActorBas> selectNewTopActorList(HashMap<String,Object> map);
	
	List<ATSMovieBas> selectMovieList(HashMap<String,Object> map);
	int selectMovieTotalCount(HashMap<String,Object> map);
	
	List<ATSMovieBas> selectSearchMovieList(HashMap<String,Object> map);
	int selectSearchMovieTotalCount(HashMap<String,Object> map);
	
	ATSMovieActorBas selectActorDetail(HashMap<String,Object> map);
	List<ATSMovieActorBas> selectMovieActorList(HashMap<String,Object> map);
	ATSMovieBas selectMovieBas(HashMap<String,Object> map);
	List<ATSMovieBas> selectSimilarMovieList(HashMap<String,Object> map);
	
	int insertMovieReactionTxnUp(ATSMovieReactionTxn movieReaction);
	int updateMovieBasUp(int movId);
	int insertMovieReactionTxnDown(ATSMovieReactionTxn movieReaction);
	int updateMovieBasDown(int movId);
}
