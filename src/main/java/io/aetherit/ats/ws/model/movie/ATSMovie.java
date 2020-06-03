package io.aetherit.ats.ws.model.movie;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovie {
	private HashMap<String,String> address;
    private ATSCover allCovers;
    private ATSCover allJCovers;
    private String cover;
    private int id;
    private String jThumbnail;
    private LocalTime mins;
    private String movName;
    private String movScore;
    private HashMap<String,String> movSize;
    private String movSn;
    private String movSnOri;
    private int playCnt;
    private String primevalCover;
    private List<ATSMovieTag> relTagName;
    private int score;
    private String selector;
    private String strPlayCnt;
    private String thumbnail;
    private long userId;
    private int vipFlag;
}
