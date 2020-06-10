package io.aetherit.ats.ws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.aetherit.ats.ws.exception.AtsCustomException;
import io.aetherit.ats.ws.model.ATSVerify;
import io.aetherit.ats.ws.repository.VerifyRepository;

@Service
public class VerifyService {

	private VerifyRepository verifyRepository;
	
	@Autowired
	VerifyService(VerifyRepository verifyRepository){
		this.verifyRepository = verifyRepository;
	}
	
	public int insertVerify(ATSVerify verfiy) {
		int result = verifyRepository.insertVerify(verfiy);
		return result;
	}
	
	public boolean checkVerify(ATSVerify verify) throws AtsCustomException {
		boolean result = verifyRepository.checkVerify(verify);
		if(!result) throw new AtsCustomException("Verification Error", HttpStatus.BAD_REQUEST);
		return result;
	}

	public int deleteVerify(String phoneNo) {
		return verifyRepository.deleteVerify(phoneNo);		
	}
	
 
}