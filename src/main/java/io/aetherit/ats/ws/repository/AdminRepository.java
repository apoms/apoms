package io.aetherit.ats.ws.repository;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.dao.ATSLiveGiftBas;
import io.aetherit.ats.ws.model.dao.ATSMovieBas;
import io.aetherit.ats.ws.repository.mapper.AdminMapper;

@Repository
public class AdminRepository {
    private AdminMapper adminMapper;

    @Autowired
    public AdminRepository(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public int insertMovieBas(ATSMovieBas movieBas) {
        return adminMapper.insertMovieBas(movieBas);
    }
    
    public int insertMovieTagDtl(HashMap<String,Object> map) {
    	return adminMapper.insertMovieTagDtl(map);
    }
    
    public int insertMovieCoverImage(HashMap<String,Object> map) {
    	return adminMapper.insertMovieCoverImage(map);
    }
    
    public int updateMovieBas(HashMap<String,Object> map) {
    	return adminMapper.updateMovieBas(map);
    }
    
    public int approvalUserTypeAnchor(HashMap<String,Object> map) {
        return adminMapper.approvalUserTypeAnchor(map);
    }
    
    public int insertLiveGift(ATSLiveGiftBas liveGift) {
    	return adminMapper.insertLiveGift(liveGift);
    }
}



