package io.aetherit.ats.ws.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.common.ATSFollower;
import io.aetherit.ats.ws.repository.mapper.UserMapper;

@Repository
public class UserRepository {
    private UserMapper mapper;

    @Autowired
    public UserRepository(UserMapper mapper) {
        this.mapper = mapper;
    }

    public ATSUser selectUser(long userid) {
        return mapper.selectUser(userid);
    }
    
    public ATSUser selectUserByEmail(String email) {
    	return mapper.selectUserByEmail(email);
    }
    
    public ATSUser selectUserByPhoneNo(String phoneNo) {
    	return mapper.selectUserByPhoneNo(phoneNo);
    }
    
    public List<ATSUser> selectUsers(HashMap<String,Object> map) {
        return mapper.selectUsersWhereType(map);
    }
    
    public int insertUser(ATSUser user) {
        return mapper.insertUser(user);
    }
    
    public int updateUserTypeAnchor(ATSUser user) {
    	return mapper.updateUserTypeAnchor(user);
    }
    
    public List<ATSFollower> selectFollowerList(long userid) {
    	return mapper.selectFollowerList(userid);
    }
    
    public List<ATSFollower> selectFollowingList(long userid) {
    	return mapper.selectFollowingList(userid);
    }
}
