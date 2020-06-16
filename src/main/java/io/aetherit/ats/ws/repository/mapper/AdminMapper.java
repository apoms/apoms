package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;

import io.aetherit.ats.ws.model.dao.ATSLiveGiftBas;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;

public interface AdminMapper {
	int insertMovieBas(ATSMovieBas movieBas);
	int insertMovieTagDtl(HashMap<String,Object> map);
	int insertMovieCoverImage(HashMap<String,Object> map);
	int updateMovieBas(HashMap<String,Object> map);
	int approvalUserTypeAnchor(HashMap<String,Object> map);
	int insertLiveGift(ATSLiveGiftBas liveGift);
}
