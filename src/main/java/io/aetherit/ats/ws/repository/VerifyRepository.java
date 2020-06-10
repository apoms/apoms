package io.aetherit.ats.ws.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.ATSVerify;
import io.aetherit.ats.ws.repository.mapper.VerifyMapper;

@Repository
public class VerifyRepository {

    
    private VerifyMapper mapper;

    @Autowired
    public VerifyRepository(VerifyMapper mapper) {
        this.mapper = mapper;
    }
    
    public int insertVerify(ATSVerify verify){
    	return mapper.insertVerify(verify);
    }
    public boolean selectVerifyByEPhone(String phoneNo) {
    	return mapper.selectVerifyByPhone(phoneNo);
    }
    public boolean selectVerifyByPhoneForLimitRquest(String phoneNo) {
    	return mapper.selectVerifyByPhoneForLimitRquest(phoneNo);
    }
    public boolean checkVerify(ATSVerify verify){
    	return mapper.checkVerify(verify);
    }
    public int deleteVerify(String phoneNo){
    	return mapper.deleteVerify(phoneNo);
    }
    
}