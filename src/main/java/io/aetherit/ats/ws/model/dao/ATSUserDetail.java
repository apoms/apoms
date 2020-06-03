package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;

import io.aetherit.ats.ws.model.type.ATSUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSUserDetail {
    private String userId;
    private String uploadUserIconDomain;
    private String uploadUserIcon;
    private String userDesc;
    private String userStatus;
    private String uploadUserFans;
    private String collectionCnt;
    private String followerCnt;
    private String point_amt;
    private String uploaderInviteCode;
    private String gender;
    private String age;
    private String career;
    private String marriage;
    private String selectLang;
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
}
