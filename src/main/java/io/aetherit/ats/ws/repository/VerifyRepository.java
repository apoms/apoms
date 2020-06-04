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
    public boolean selectVerifyByEmail(String email) {
    	return mapper.selectVerifyByEmail(email);
    }
    public boolean selectVerifyByEmailForLimitRquest(String email) {
    	return mapper.selectVerifyByEmailForLimitRquest(email);
    }
    public boolean checkVerify(ATSVerify verify){
    	return mapper.checkVerify(verify);
    }
    public int deleteVerify(String email){
    	return mapper.deleteVerify(email);
    }
    
}