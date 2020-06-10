package io.aetherit.ats.ws.repository.mapper;

import io.aetherit.ats.ws.model.ATSVerify;

public interface VerifyMapper {
	
	int insertVerify(ATSVerify verify);
    boolean selectVerifyByPhone(String phone);
    boolean selectVerifyByPhoneForLimitRquest(String phone);
    boolean checkVerify(ATSVerify verify);
    int deleteVerify(String phone);
    
	
}
