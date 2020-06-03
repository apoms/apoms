package io.aetherit.ats.ws.model.main;

import java.util.List;

import io.aetherit.ats.ws.model.actor.ATSActor;
import io.aetherit.ats.ws.model.movie.ATSRanking;
import io.aetherit.ats.ws.model.movie.ATSRecommand;
import io.aetherit.ats.ws.model.movie.ATSWonderful;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSCombine {
    private List<ATSActor> recommandActorList;		// 2명
    private List<ATSRanking> todayRankingdMov;			// 10개
    private List<ATSRecommand> todayRecommandMov;		// 10개
    private List<ATSWonderful> wonderfulMov;			// 10개
}
