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
public class ATSNewMovie {
    private HashMap<String,String> address;
    private ATSCover allCovers;
    private ATSCover allJCovers;
    private String cover;
    
    private boolean hasDownload;
    private boolean hasFavor;
    
    private int id;
    
    private String jCover;
    private String jThumbnail;
    private LocalTime mins;
    private String movName;
    private String movScore;
    private HashMap<String,String> movSize;
    private String movSnOri;
    private int playCnt;
    private String playCntStr;
    private List<ATSMovieTag> relTag;
    private String thumbnail;
    private long userId;
    private int vipFlag;
}
