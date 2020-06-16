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
public class ATSWebrtcServer {
    private long serverId;
    private String serverName;
    private int maxUsers;
    private boolean activationFlag;
    private String comment;
    private int priority;
    private String webrtcUrl;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
}
