package io.aetherit.ats.ws.model.live;

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
public class ATSLiveRoomRanking {
    private long roomId;
    private long userId;
    private String roomDesc;
    private ATSLiveRoomType typeCode;
    private ATSLiveRoomStatus statusCode;
    private String thumbnailUrl;
    private LocalDateTime regDt;
    private int publishTime;
    private int joinedCount;
}
