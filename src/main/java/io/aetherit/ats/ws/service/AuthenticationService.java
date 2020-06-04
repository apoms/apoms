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
import io.aetherit.ats.ws.model.ATSSimpleUser;
import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.ATSUserToken;
import io.aetherit.ats.ws.model.ATSVerify;
import io.aetherit.ats.ws.repository.UserRepository;
import io.aetherit.ats.ws.repository.VerifyRepository;
import io.aetherit.ats.ws.util.CommonUtil;
import io.aetherit.ats.ws.util.MailTemplate;


@Service
public class AuthenticationService {
	
	private static final String senderEmail = "support@onthe.live";
	
    private UserAuthenticationProvider authenticationManager;
    private UserRepository userRepository;
    private VerifyRepository verifyRepository;
    private CommonUtil commonUtil;
    private MailTemplate mailTemplate;

    @Autowired
    public AuthenticationService( UserAuthenticationProvider authenticationManager
    							, UserRepository userRepository
    							, VerifyRepository verifyRepository
    							, CommonUtil commonUtil
    							, MailTemplate mailTemplate) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.verifyRepository = verifyRepository;
        this.commonUtil = commonUtil;
        this.mailTemplate = mailTemplate;
    }

	public ATSUserToken getToken(String email, String rawPassword, HttpSession session) {
        final Authentication request = new UsernamePasswordAuthenticationToken(email, rawPassword);
        final Authentication result = authenticationManager.authenticate(request);
        
        if ((result != null) && (result.isAuthenticated())) {
            SecurityContextHolder.getContext().setAuthentication(result);
        } else {
            return null;
        }
//        ATSSimpleUser simpleUser = (ATSSimpleUser) result.getDetails();

        return ATSUserToken.builder()
                .token(session.getId())
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
    
	public boolean checkVerifyEmail(ATSVerify verify) {
		boolean result = verifyRepository.checkVerify(verify);
		if(!result) throw new AtsCustomException("Verification Error", HttpStatus.BAD_REQUEST);
		return result;
	}
	
	@Transactional
	public boolean requestVerifyCodeEmail(String email) throws Exception {
		
		int key = new Random().nextInt(999999);;
		String randomNumber = String.valueOf(key);
		
		ATSUser user = userRepository.selectUserByEmail(email);
		if( user != null )
			throw new AtsCustomException("Email Exsit");
		
		//check Authentication already 
		verifyRepository.deleteVerify(email);
		
		//insertVerify
		boolean result = false; 
		while(!result) {
			result = insertVerify(ATSVerify.builder().authenticationKey(randomNumber).email(email).build());
		}
		
		return sendAuthMail(email, randomNumber); 
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
			Body body = new Body().withText(bodyContent);
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
