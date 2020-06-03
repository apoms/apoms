package io.aetherit.ats.ws.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.aetherit.ats.ws.exception.NotAcceptableIdException;
import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.type.ATSUserType;
import io.aetherit.ats.ws.repository.UserRepository;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

//    private static final String DEFAULT_ADMIN_ID = "admin";
    private static final String DEFAULT_ADMIN_ID = "99999999999";
    private static final String DEFAULT_ADMIN_PASSWORD = "1234";
    private static final String DEFAULT_ADMIN_NAME = "administrator";
    private static final String DEFAULT_PHONE_NO = "99999999999";
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
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
                    .userName(DEFAULT_ADMIN_NAME)
                    .phoneNo(DEFAULT_PHONE_NO)
                    .type(ATSUserType.ADMIN)
                    .build();

            createNewUser(newAdmin);
        }
    }

    public ATSUser getUser(String id) {
        return repository.selectUser(id);
    }

    public List<ATSUser> getUsers(ATSUserType type) {
        return repository.selectUsers(type);
    }
    
    public ATSUser getUserByPhoneNo(String phoneNo) {
        return repository.selectUserByPhoneNo(phoneNo);
    }

    public ATSUser createNewUser(ATSUser user) {
        if(isNotAcceptableId(user.getUserId())) {
            throw new NotAcceptableIdException(user.getUserId());
        }
        final String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setRegDt(LocalDateTime.now());
        user.setModDt(LocalDateTime.now());

        repository.insertUser(user);

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