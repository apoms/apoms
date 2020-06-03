package io.aetherit.ats.ws.model.movie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.actor.ATSSimpleActor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieBrowse {
	private String actor;
	private List<ATSSimpleActor> actorList;
	private HashMap<String,String> address;
    private ATSCover allCovers;
    private ATSCover allJCovers;
    
    private int attenStatus;
	private String browId;
	private String browToken;
	
    private String cover;
    private String director;
    private String domain;
    private String encryKey;
    private LocalDate gmtCreate;
    private boolean hasDiss;
    private boolean hasDown;
    private boolean hasFavor;
    private boolean hasUp;
    
    private int id;
    private boolean isMosaic;
    private int loveCnt;
    private LocalTime mins;
    
    private int movCls;
    private String movDesc;
    private String movName;
    private String movScore;
    private HashMap<String,String> movSize;
    private String movSn;
    private String movSnOri;
    private int originalMovid;
    
    private String p2pSharpness;
    private String jThumbnail;
    private String p2pToken;
    
    private int playCnt;
    private int remainPlayCnt;
    private String strPlayCnt;
    private List<ATSMovieTag> tags;
    
    private int uploadUserFans;
    private String uploadUserIcon;
    private String uploadUserName;
    private String uploaderInviteCode;
    private long userId;
    private int vipFlag;
    private int ydaPlayCnt;
}
