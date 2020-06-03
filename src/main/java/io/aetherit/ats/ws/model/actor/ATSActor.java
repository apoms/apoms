package io.aetherit.ats.ws.model.actor;

import java.util.List;

import io.aetherit.ats.ws.model.movie.ATSActorMovie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSActor {
    private ATSActorDTO actorDTO;
    private List<ATSActorMovie> movieList;
}

