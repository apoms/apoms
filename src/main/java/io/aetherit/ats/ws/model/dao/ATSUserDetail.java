package io.aetherit.ats.ws.model.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSUserDetail {
    private long userId;
    private String uploadUserIconDomain;
    private String uploadUserIcon;
    private String userDesc;
    private String userStatus;
    private int uploadUserFans;
    private int collectionCnt;
    private int followerCnt;
    private BigDecimal pointAmt;
    private String uploaderInviteCode;
    private String gender;
    private int age;
    private String career;
    private String marriage;
    private String selectLang;
    private long regId;
    private LocalDateTime regDt;
    private long modId;
    private LocalDateTime modDt;
}
