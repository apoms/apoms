package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.type.ATSUserType;

public interface UserMapper {
    ATSUser selectUser(String userid);
    ATSUser selectUserByPhoneNo(String phoneNo);
    
    List<ATSUser> selectUsersWhereType(ATSUserType type);
    
    List<ATSUser> selectUserList(HashMap<String, Object> map);
    int selectUserTotalCount(HashMap<String, Object> map);
    
    int insertUser(ATSUser account);
}
