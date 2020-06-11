package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;

import io.aetherit.ats.ws.model.type.ATSUserRelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSUserRel {
    private int followIdx;
    private long userId;
    private long relId;
    private ATSUserRelType relType;
    private LocalDateTime relDt;
}
