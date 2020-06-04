package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;

import io.aetherit.ats.ws.model.type.ATSLiveRoomStatus;
import io.aetherit.ats.ws.model.type.ATSLiveRoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSLiveRoom {
    private long roomId;
    private long userId;
    private ATSLiveRoomType typeCode;
    private ATSLiveRoomStatus statusCode;
    private String thumnailUrl;
    private LocalDateTime regDt;
}
