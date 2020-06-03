package io.aetherit.ats.ws.model.movie;

import java.time.LocalTime;
import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSActorMovie {
	private HashMap<String,String> address;
    private ATSCover cover;
    private int id;
    private String jThumbnail;
    private LocalTime mins;
    private String movName;
    private String movScore;
    private HashMap<String,String> movSize;
    private String movSnOri;
    private String playCnt;
    private ATSCover primevalCover;
    private String strPlayCnt;
    private String thumbnail;
    private int vipFlag;
}
