package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSMovieBas {
	private int movId;
	private String movName;
    private LocalTime mins;
    private int playCnt;
    private String playCntStr;
    private int downCnt;
    private boolean hasDown;
    
    private int loveCnt;
    private boolean hasLove;
    private int upCnt;
    private boolean hasUp;
    private int dissCnt;
    private boolean hasDiss;
    private String movSn;
    private String movSnOri;
    private String movType;
    private boolean movProvider;
    private String domain;
    private String P240;
    private String P360;
    private String P480;
    private String P720;
    private String P1080;
    private String movDesc;
    private boolean isMosaic;
    private int movScore;
    private int giftTotal;
    private boolean showYn;
    private int showOrder;
    private LocalDateTime recommendDate;
    private String recommendDesc;
    private boolean vipFlag;
    private long gmtCreateId;
    private LocalDateTime gmtCreate;
    private long modId;
    private LocalDateTime modDt;
}


