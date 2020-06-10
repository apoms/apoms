package io.aetherit.ats.ws.repository.mapper;

import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSAdvertisiong;

public interface AdvertisingMapper {
	List<ATSAdvertisiong> selectAdsList(String langCd);
}
