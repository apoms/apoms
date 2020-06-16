package io.aetherit.ats.ws.model.live;

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
public class ATSLiveRoomPatch {
    private long roomId;
    private ATSLiveRoomType typeCode;
    private ATSLiveRoomStatus statusCode;
    private String thumbnailUrl;
}
