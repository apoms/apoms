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
public class ATSMovieActorRel {
	private int movId;
	private int actorIdx;
    private long modId;
    private LocalDateTime modDt;
}
