package io.aetherit.ats.ws.model.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieActor {
    private int actorIdx;
    private String actorName;
}
