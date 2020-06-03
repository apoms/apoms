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
public class ATSRanking {
	private HashMap<String,String> address;
    private int arrowState;
    private int collectNumber;
    private int downloadNumber;
    private LocalTime mins;
    private int movId;
    private HashMap<String,String> movSize;
    private int mov_index;
    private String name;						//제목
    private ATSCover oriUrl;
    private int playNumber;
    private int ranking;
    private int release_time;
    private String type;
    private ATSCover url;
    private int vipFlag;
}
