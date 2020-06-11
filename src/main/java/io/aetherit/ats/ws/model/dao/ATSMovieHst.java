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
public class ATSMovieHst {
    private long hstIdx;
    private int movId;
    private long userId;
    private ATSUserType typeCode;					// default : ANONYMOUS
    private ATSHistoryTypeCode historyTypeCode;		// default : JOIN
    private LocalDateTime regDt;
}
