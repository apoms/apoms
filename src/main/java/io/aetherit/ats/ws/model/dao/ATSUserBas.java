package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;

import io.aetherit.ats.ws.model.type.ATSUserStatus;
import io.aetherit.ats.ws.model.type.ATSUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSUserBas {
    private long userId;
    private String pwd;
    private String nickName;
    private String cntryCode;
    private String phoneNo;
    private ATSUserType userType;
    private ATSUserStatus statusCode;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
}
