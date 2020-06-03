package io.aetherit.ats.ws.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.type.ATSUserType;
import io.aetherit.ats.ws.repository.mapper.UserMapper;

@Repository
public class UserRepository {
    private UserMapper mapper;

    @Autowired
    public UserRepository(UserMapper mapper) {
        this.mapper = mapper;
    }

    public ATSUser selectUser(String userid) {
        return mapper.selectUser(userid);
    }
    
    public ATSUser selectUserByPhoneNo(String phoneNo) {
    	return mapper.selectUserByPhoneNo(phoneNo);
    }
    
    public List<ATSUser> selectUsers(ATSUserType type) {
        return mapper.selectUsersWhereType(type);
    }
    
    public int insertUser(ATSUser user) {
        return mapper.insertUser(user);
    }
    
}
