package io.aetherit.ats.ws.service;

import java.util.Arrays;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import io.aetherit.ats.ws.exception.AtsCustomException;
import io.aetherit.ats.ws.exception.UserPhoneDuplicationException;
import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.ATSUserSignIn;
import io.aetherit.ats.ws.model.ATSUserToken;
import io.aetherit.ats.ws.model.ATSVerify;
import io.aetherit.ats.ws.repository.UserRepository;
import io.aetherit.ats.ws.repository.VerifyRepository;
import io.aetherit.ats.ws.util.CommonUtil;
import io.aetherit.ats.ws.util.JwtTokenUtil;
import io.aetherit.ats.ws.util.MailTemplate;


@Service
public class AuthenticationService {
	
	private static final String senderEmail = "support@onthe.live";
	
    private UserAuthenticationProvider authenticationManager;
    private UserRepository userRepository;
    private VerifyRepository verifyRepository;
    private CommonUtil commonUtil;
    private JwtTokenUtil jwtTokenUtil;
    private MailTemplate mailTemplate;

    @Autowired
    public AuthenticationService( UserAuthenticationProvider authenticationManager
    							, UserRepository userRepository
    							, VerifyRepository verifyRepository
    							, CommonUtil commonUtil
    							, JwtTokenUtil jwtTokenUtil
    							, MailTemplate mailTemplate) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.verifyRepository = verifyRepository;
        this.commonUtil = commonUtil;
        this.jwtTokenUtil = jwtTokenUtil;
        this.mailTemplate = mailTemplate;
    }
    
    @SuppressWarnings("static-access")
    public ATSUserToken getAnonymousToken(HttpSession session) {
    	final String cntryCode = "+86";
    	final String phoneNo = "01000000000000";
    	final String passwd = "anonymous";
        final Authentication request = new UsernamePasswordAuthenticationToken(cntryCode+phoneNo, passwd);
        final Authentication result = authenticationManager.anonymousAuthenticate(request,passwd);
        
        if ((result != null) && (result.isAuthenticated())) {
            SecurityContextHolder.getContext().setAuthentication(result);
        } else {
            return null;
        }
        ATSSimpleUser simpleUser = (ATSSimpleUser) result.getDetails();
        
        String jwt = jwtTokenUtil.getJwtToken(simpleUser);

        return ATSUserToken.builder()
                .token(session.getId())
                .chatToken(jwt)
                .user((ATSSimpleUser) result.getDetails())
                .build();
    }

	@SuppressWarnings("static-access")
	public ATSUserToken getToken(ATSUserSignIn account, HttpSession session) {
        final Authentication request = new UsernamePasswordAuthenticationToken(account.getCntryCode()+account.getName(), account.getPassword());
        final Authentication result = authenticationManager.authenticate(request);
        
        if ((result != null) && (result.isAuthenticated())) {
            SecurityContextHolder.getContext().setAuthentication(result);
        } else {
            return null;
        }
        ATSSimpleUser simpleUser = (ATSSimpleUser) result.getDetails();
        String jwt = jwtTokenUtil.getJwtToken(simpleUser);

        return ATSUserToken.builder()
                .token(session.getId())
                .chatToken(jwt)
                .user((ATSSimpleUser) result.getDetails())
                .build();
    }
    

    public ATSSimpleUser getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (ATSSimpleUser) authentication.getDetails();
    }

    
    public boolean insertVerify(ATSVerify verify) {
		int result = verifyRepository.insertVerify(verify);
		return result == 1;
	}
    
	public boolean checkVerifyPhoneNo(ATSVerify verify) {
		boolean result = verifyRepository.checkVerify(verify);
		if(!result) throw new AtsCustomException("Verification Error", HttpStatus.BAD_REQUEST);
		return result;
	}
	
	
	@Transactional
//	public boolean requestVerifyCodePhoneNo(ATSUserSignIn verify) throws Exception {
	public String requestVerifyCodePhoneNo(ATSUserSignIn verify) throws Exception {
		
		int key = new Random().nextInt(9999);;
		String randomNumber = String.valueOf(key);
		String phoneNo = verify.getCntryCode()+verify.getName();
		
		ATSUser user = userRepository.selectUserByPhoneNo(phoneNo);
		if( user != null )
			throw new UserPhoneDuplicationException(phoneNo);
		
		//check Authentication already 
		verifyRepository.deleteVerify(phoneNo);
		
		//insertVerify
		boolean result = false; 
		while(!result) {
			result = insertVerify(ATSVerify.builder().authenticationKey(randomNumber).phoneNo(phoneNo).build());
		}
		
//		return result; 
		return randomNumber; 
	}
	
	
//	public boolean verifyForPassword(ATSUserSignIn verify) throws AtsCustomException {
	public String verifyForPassword(ATSUserSignIn verify) throws AtsCustomException {
		
		int key = new Random().nextInt(9999);;
		String randomNumber = String.valueOf(key);
		String phoneNo = verify.getCntryCode()+verify.getName();
		
		//CHECK ATS USER?
		ATSUser user = userRepository.selectUserByPhoneNo(phoneNo);
		if(user == null) {
			throw new AtsCustomException("Can't found user", HttpStatus.BAD_REQUEST);
		}
//		//BLOCK 10 MIN REQUEST
//		boolean isAlready = verifyRepository.selectVerifyByPhoneForLimitRquest(phoneNo);
//		if(isAlready) {
//			throw new AtsCustomException("Already have Authentication, check you'r message, try again after 10 mins", HttpStatus.BAD_REQUEST);		}
		
		//check Authentication already 
		verifyRepository.deleteVerify(phoneNo);
		
		//insertVerify
		boolean result = false; 
		while(!result) {
			result = insertVerify(ATSVerify.builder().authenticationKey(randomNumber).phoneNo(phoneNo).build());
		}
		
//		return result; 
		return randomNumber; 
	}
    
	
	/**
	 * 사용자 등록 인증메일 전송
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean sendAuthMail(String email,String randomNumber) {
    	
		try{
			AmazonSimpleEmailService sesClient = commonUtil.getAwsSesMailCredentials();
			
			Content subjectContent = new Content("UPAY Authentication Service");
			Content bodyContent = new Content(mailTemplate.authMailTemplate(email,randomNumber));
			Body body = new Body().withHtml(bodyContent);
			Destination destination = new Destination(Arrays.asList(email));
			Message message = new Message(subjectContent, body);
			SendEmailRequest request = new SendEmailRequest()
					.withSource(senderEmail)
					.withDestination(destination)
					.withMessage(message);
			sesClient.sendEmail(request);
		} catch (Exception e) {
		    System.out.println("The email was not sent. Error message: " 
		          + e.getMessage());
		    e.printStackTrace();
		    throw new AtsCustomException(e.getMessage());
		}
		return true; 
    }
    

}
