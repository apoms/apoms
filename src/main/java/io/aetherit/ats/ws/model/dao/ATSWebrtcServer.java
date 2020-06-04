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
    private long server_id;
    private String server_name;
    private int max_users;
    private boolean activation_flag;
    private String comment;
    private int priority;
    private String webrtc_url;
    private LocalDateTime reg_dt;
    private LocalDateTime mod_dt;
}
