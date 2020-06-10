package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;

import io.aetherit.ats.ws.model.type.ATSHistoryTypeCode;
import io.aetherit.ats.ws.model.type.ATSUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSLiveRoomUserHst {
    private long roomUserHistoryId;
    private long roomId;
    private long userId;
    private ATSUserType typeCode;					// default : ANOYMOUS
    private ATSHistoryTypeCode historyTypeCode;		// default : JOIN
    private LocalDateTime regDt;
}
