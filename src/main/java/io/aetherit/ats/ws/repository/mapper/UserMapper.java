package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.common.ATSFollower;

public interface UserMapper {
    ATSUser selectUser(long userid);
    ATSUser selectUserByEmail(String email);
    ATSUser selectUserByPhoneNo(String phoneNo);
    
    List<ATSUser> selectUsersWhereType(HashMap<String,Object> map);
    
    List<ATSUser> selectUserList(HashMap<String, Object> map);
    int selectUserTotalCount(HashMap<String, Object> map);
    
    int insertUser(ATSUser user);
    int updateUserTypeAnchor(ATSUser user);
    
    List<ATSFollower> selectFollowerList(long userid);
    List<ATSFollower> selectFollowingList(long userid);
}
