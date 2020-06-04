package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSServerRoom {
    private long roomId;
    private long serverId;
    private LocalDateTime regDt;
}
