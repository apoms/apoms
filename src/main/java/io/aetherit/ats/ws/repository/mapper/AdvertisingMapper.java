package io.aetherit.ats.ws.repository.mapper;

import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSAdvertisiong;
import io.aetherit.ats.ws.model.type.ATSLangCode;

public interface AdvertisingMapper {
	List<ATSAdvertisiong> selectAdsList(ATSLangCode langCd);
}
