package io.aetherit.ats.ws.model.live;

import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSWebrtcServer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSLiveRoomServer {
    private ATSLiveRoom liveRoom;
    private ATSWebrtcServer webrtcServer;
    private ATSUser anchor;
    private int popularity;
}
