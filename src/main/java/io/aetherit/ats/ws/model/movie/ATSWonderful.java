package io.aetherit.ats.ws.model.movie;

import java.time.LocalDateTime;
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
public class ATSWonderful {
	private HashMap<String,String> address;
	private String aliasName;
    private int browCntStr;
    private String cover;
    private String disCnt;
    private LocalDateTime gmtCreate;
    private boolean hasUp;
    private String icon;
    private long id;
    private LocalTime mins;
    private int movId;
    private String myInviteCode;
    private String name;
    private int originalMovid;
    
    private String primevalCover;
    private int size;
    private String thumbnail;
    private int type;
    private int uId;
    private int upCnt;
}
