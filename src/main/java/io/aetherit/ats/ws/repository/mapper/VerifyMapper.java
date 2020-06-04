package io.aetherit.ats.ws.repository.mapper;

import io.aetherit.ats.ws.model.ATSVerify;

public interface VerifyMapper {
	
	int insertVerify(ATSVerify verify);
    boolean selectVerifyByEmail(String email);
    boolean selectVerifyByEmailForLimitRquest(String email);
    boolean checkVerify(ATSVerify verify);
    int deleteVerify(String email);
    
	
}
