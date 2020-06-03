package io.aetherit.ats.ws.model.actor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSActorRanking {
    private int actorsId;
    private int arrowState;
    private int movNumber;
    private String name;
    private String photoUrl;
    private int ranking;
    private String starLevel;
    private int type;
}

