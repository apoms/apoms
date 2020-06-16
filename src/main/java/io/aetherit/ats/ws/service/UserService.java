package io.aetherit.ats.ws.service;

import java.math.BigDecimal;
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
import org.springframework.transaction.annotation.Transactional;

import io.aetherit.ats.ws.exception.AtsCustomException;
import io.aetherit.ats.ws.exception.CanNotFoundUserException;
import io.aetherit.ats.ws.exception.NotAcceptableIdException;
import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.ATSUserSignUp;
import io.aetherit.ats.ws.model.ATSVerify;
import io.aetherit.ats.ws.model.common.ATSFollower;
import io.aetherit.ats.ws.model.dao.ATSUserDetail;
import io.aetherit.ats.ws.model.dao.ATSUserRel;
import io.aetherit.ats.ws.model.type.ATSUserStatus;
import io.aetherit.ats.ws.model.type.ATSUserType;
import io.aetherit.ats.ws.repository.UserRepository;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    
    private static final long DEFAULT_ADMIN_ID = 999999999;
    private static final String DEFAULT_NICK_NAME = "admin";
    private static final String DEFAULT_ADMIN_PASSWORD = "1234";
    private static final String DEFAULT_ADMIN_CNTRY_CODE = "+82";
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
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("type", ATSUserType.ADMIN);
    	map.put("statusCode", "");
    	
        final List<ATSUser> users = getUsers(map);

        if((users == null) || (users.size() < 1)) {
            logger.info("Admin account not exists : create a default admin account");

            final ATSUser newAdmin = ATSUser.builder()
                    .userId(DEFAULT_ADMIN_ID)
                    .password(DEFAULT_ADMIN_PASSWORD)
                    .nickName(DEFAULT_NICK_NAME)
                    .cntryCode(DEFAULT_ADMIN_CNTRY_CODE)
                    .phoneNo(DEFAULT_PHONE_NO)
                    .type(ATSUserType.ADMIN)
                    .statusCode(ATSUserStatus.NORMAL)
                    .build();

            createNewUser(ATSUserSignUp.builder().user(newAdmin).verificationCode("9999").build());
        }
    }

    /**
     * get user one
     * @param id
     * @return
     */
    public ATSUser getUser(long userId) {
        return repository.selectUser(userId);
    }
    
    /**
     * get user Detail 
     * @param id
     * @return
     */
    public ATSUserDetail getUserDetail(long id) {
        return repository.selectUserDetail(id);
    }

    /**
     * user List by type
     * @param type
     * @return
     */
    public List<ATSUser> getUsers(HashMap<String,Object> map) {
        return repository.selectUsers(map);
    }
    
    /**
     * user by email
     * @param email
     * @return
     */
    public ATSUser getUserByEmail(String email) {
        return repository.selectUserByEmail(email);
    }
    
    /**
     * user by phone no
     * @param phoneNo
     * @return
     */
    public ATSUser getUserByPhoneNo(String phoneNo) {
    	return repository.selectUserByPhoneNo(phoneNo);
    }

    /**
     * create new user
     * @param userSignUp
     * @return
     */
    @Transactional
    public ATSUser createNewUser(ATSUserSignUp userSignUp) {
    	
    	ATSUser user = userSignUp.getUser();
    	String verifyPhoneNo = user.getCntryCode()+user.getPhoneNo();
    	
		ATSVerify verify = ATSVerify.builder()
									.phoneNo(verifyPhoneNo)
									.authenticationKey(userSignUp.getVerificationCode())
									.build();

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
			ATSUser checkUser = repository.selectUserByPhoneNo(user.getCntryCode()+user.getPhoneNo());
			if(checkUser != null ) {
				throw new AtsCustomException("User Already", HttpStatus.BAD_REQUEST);
			}
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//PRIORITY INSERT USER
		repository.insertUser(user);
		repository.insertUserDtl(user);
		
		try {
			verifyService.deleteVerify(user.getPhoneNo());
		}catch(Exception e) {
			throw new AtsCustomException(e.getLocalizedMessage());
		}
		return user; 
    }
    
    /**
     * request user type update to anchor
     * @param id
     * @return
     */
    public ATSUser setUserTypeAnchor(long id) {
    	ATSUser user = repository.selectUser(id);
    	if(user==null) {
    		throw new CanNotFoundUserException(id+"");
    	}
    	
    	user.setType(ATSUserType.ANCHOR);
    	user.setStatusCode(ATSUserStatus.WAIT);
    	
    	repository.updateUserTypeAnchor(user);
    	user.setPassword("");
    	return user;
    }
    
    public List<ATSFollower> getFollowerList(long userId) {
    	return repository.selectFollowerList(userId);
    }
    
    public List<ATSFollower> getFollowingList(long userId) {
    	return repository.selectFollowingList(userId);
    }
    
    public void setFollowing(ATSUserRel userRel) {
    	repository.insertFollowing(userRel);
    }
    
    public void deleteFollowing(int followIdx) {
    	repository.deleteFollowing(followIdx);
    }
    
    
    public void decreaseUserPoint(long userId, BigDecimal point) {
    	repository.decreaseUserPoint(userId, point);
    }
    
    public void increaseUserPoint(long userId, BigDecimal point) {
    	repository.increaseUserPoint(userId, point);
    }
    
    
    /**
     * acceptable check
     * @param id
     * @return
     */
    private boolean isNotAcceptableId(String id) {
        boolean isNotAcceptable = false;

        if((id == null) || (id.length() < 1) || (id.contains(" ")) || (notAcceptableIdMap.containsKey(id.toLowerCase()))) {
            isNotAcceptable = true;
        }

        return isNotAcceptable;
    }
    
}