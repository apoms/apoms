package io.aetherit.ats.ws.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.common.ATSFollower;
import io.aetherit.ats.ws.model.dao.ATSUserDetail;
import io.aetherit.ats.ws.model.dao.ATSUserRel;
import io.aetherit.ats.ws.repository.mapper.UserMapper;

@Repository
public class UserRepository {
    private UserMapper mapper;

    @Autowired
    public UserRepository(UserMapper mapper) {
        this.mapper = mapper;
    }

    public ATSUser selectUser(long userId) {
        return mapper.selectUser(userId);
    }
    
    public ATSUserDetail selectUserDetail(long userId) {
    	return mapper.selectUserDetail(userId);
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
    
    public int insertUserDtl(ATSUser user) {
    	return mapper.insertUserDtl(user);
    }
    
    public int updateUserTypeAnchor(ATSUser user) {
    	return mapper.updateUserTypeAnchor(user);
    }
    
    public List<ATSFollower> selectFollowerList(long userId) {
    	return mapper.selectFollowerList(userId);
    }
    
    public List<ATSFollower> selectFollowingList(long userId) {
    	return mapper.selectFollowingList(userId);
    }
    
    public int insertFollowing(ATSUserRel userRel) {
    	return mapper.insertFollowing(userRel);
    }
    
    public int deleteFollowing(int followIdx) {
    	return mapper.deleteFollowing(followIdx);
    }
    
    public int decreaseUserPoint(long userId, BigDecimal point) {
    	return mapper.decreaseUserPoint(userId, point);
    }
    
    public int increaseUserPoint(long userId, BigDecimal point) {
    	return mapper.increaseUserPoint(userId, point);
    }
}
