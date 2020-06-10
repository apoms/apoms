package io.aetherit.ats.ws.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.dao.ATSAdvertisiong;
import io.aetherit.ats.ws.repository.mapper.AdvertisingMapper;

@Repository
public class AdvertisingRepository {
    private AdvertisingMapper advertisingMapper;

    @Autowired
    public AdvertisingRepository(AdvertisingMapper advertisingMapper) {
        this.advertisingMapper = advertisingMapper;
    }

    public List<ATSAdvertisiong> selectAdsList(String langCd) {
        return advertisingMapper.selectAdsList(langCd);
    }
}



