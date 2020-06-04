package io.aetherit.ats.ws.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.aetherit.ats.ws.exception.AtsCustomException;
import io.aetherit.ats.ws.exception.NotAcceptableIdException;
import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.ATSUserSignUp;
import io.aetherit.ats.ws.model.ATSVerify;
import io.aetherit.ats.ws.model.type.ATSUserType;
import io.aetherit.ats.ws.repository.UserRepository;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

//    private static final String DEFAULT_ADMIN_ID = "admin";
    private static final long DEFAULT_ADMIN_ID = 999999999;
    private static final String DEFAULT_ADMIN_PASSWORD = "1234";
    private static final String DEFAULT_ADMIN_EMAIL = "admin@ats.ws";
    private static final String DEFAULT_PHONE_NO = "00099990000";
    private static final Map<String, Boolean> notAcceptableIdMap = new HashMap<>();
    static {
        notAcceptableIdMap.put("check", false);
        notAcceptableIdMap.put("signin", false);
        notAcceptableIdMap.put("signout", false);
        notAcceptableIdMap.put("signcheck", false);
        notAcceptableIdMap.put("login", false);
        notAcceptableIdMap.put("logout", false);
        notAcceptableIdMap.put("logincheck", false);
    }

    private UserRepository repository;
    private VerifyService verifyService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, VerifyService verifyService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.verifyService = verifyService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void checkAdmin() {
        final List<ATSUser> users = getUsers(ATSUserType.ADMIN);

        if((users == null) || (users.size() < 1)) {
            logger.info("Admin account not exists : create a default admin account");

            final ATSUser newAdmin = ATSUser.builder()
                    .userId(DEFAULT_ADMIN_ID)
                    .password(DEFAULT_ADMIN_PASSWORD)
                    .email(DEFAULT_ADMIN_EMAIL)
                    .phoneNo(DEFAULT_PHONE_NO)
                    .type(ATSUserType.ADMIN)
                    .build();

            createNewUser(ATSUserSignUp.builder().user(newAdmin).emailVerificationCode("999999").build());
        }
    }

    public ATSUser getUser(String id) {
        return repository.selectUser(id);
    }

    public List<ATSUser> getUsers(ATSUserType type) {
        return repository.selectUsers(type);
    }
    
    public ATSUser getUserByEmail(String email) {
        return repository.selectUserByEmail(email);
    }
    
    public ATSUser getUserByPhoneNo(String phoneNo) {
    	return repository.selectUserByPhoneNo(phoneNo);
    }

    public ATSUser createNewUser(ATSUserSignUp userSignUp) {
    	
    	ATSUser user = userSignUp.getUser();
		ATSVerify verify = ATSVerify.builder().email(user.getEmail()).authenticationKey(userSignUp.getEmailVerificationCode()).build();

		if(isNotAcceptableId(user.getUserId()+"")) {
            throw new NotAcceptableIdException(user.getUserId()+"");
        }
		
		if(user.getType()!=ATSUserType.ADMIN) {
			//CHECK VERIFICATION CODE
			boolean verificationResult = verifyService.checkVerify(verify);
			if(!verificationResult) {
				throw new AtsCustomException("verifycation code not valid");
			}
			
			//CHECK USER ALREADY REGIST
			ATSUser checkUser = repository.selectUserByEmail(user.getEmail());
			if(checkUser != null ) {
				throw new AtsCustomException("User Already", HttpStatus.BAD_REQUEST);
			}
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//PRIORITY INSERT USER
		repository.insertUser(user);
		
		try {
			verifyService.deleteVerify(user.getEmail());
		}catch(Exception e) {
			throw new AtsCustomException(e.getLocalizedMessage());
		}
		return user; 
    }

    private boolean isNotAcceptableId(String id) {
        boolean isNotAcceptable = false;

        if((id == null) || (id.length() < 1) || (id.contains(" ")) || (notAcceptableIdMap.containsKey(id.toLowerCase()))) {
            isNotAcceptable = true;
        }

        return isNotAcceptable;
    }
}