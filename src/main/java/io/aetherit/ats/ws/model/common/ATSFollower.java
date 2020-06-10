package io.aetherit.ats.ws.model.common;

import java.time.LocalDateTime;

import io.aetherit.ats.ws.model.type.ATSUserRelType;
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
public class ATSFollower {
    private String userId;
    private String nickName;
    private String cntryCode;
    private String phoneNo;
    private ATSUserType userType;
    private ATSUserStatus statusCode;
    private ATSUserRelType relType;
    private LocalDateTime relDt;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
}
