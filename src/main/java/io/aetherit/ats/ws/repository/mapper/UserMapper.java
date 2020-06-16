package io.aetherit.ats.ws.repository.mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.common.ATSFollower;
import io.aetherit.ats.ws.model.dao.ATSUserDetail;
import io.aetherit.ats.ws.model.dao.ATSUserRel;

public interface UserMapper {
    ATSUser selectUser(long userId);
    ATSUserDetail selectUserDetail(long userId);
    ATSUser selectUserByEmail(String email);
    ATSUser selectUserByPhoneNo(String phoneNo);
    
    List<ATSUser> selectUsersWhereType(HashMap<String,Object> map);
    
    List<ATSUser> selectUserList(HashMap<String, Object> map);
    int selectUserTotalCount(HashMap<String, Object> map);
    
    int insertUser(ATSUser user);
    int insertUserDtl(ATSUser user);
    int updateUserTypeAnchor(ATSUser user);
    
    List<ATSFollower> selectFollowerList(long userId);
    List<ATSFollower> selectFollowingList(long userId);
    int insertFollowing(ATSUserRel userRel);
    int deleteFollowing(int followIdx);
    
    int decreaseUserPoint(long userId, BigDecimal point);
    int increaseUserPoint(long userId, BigDecimal point);
}
