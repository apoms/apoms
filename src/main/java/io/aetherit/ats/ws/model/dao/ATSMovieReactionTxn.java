package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;

import io.aetherit.ats.ws.model.type.ATSReactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieReactionTxn {
    private int reactionIdx;
    private int movId;
    private String userId;
    private ATSReactionType reactionType;
    private String parentIdx;
    private String reactionDtl;
    private LocalDateTime reactionDt;
}
