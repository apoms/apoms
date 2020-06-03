package io.aetherit.ats.ws.model.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSAds {
    private String adName;
    private int adShowTime;
    private int adSkipTime;
    private int adWeight;
    private String appid;
    private String channelType;
    private int equiCls;
    private String equiClsCn;
    private String gameType;
    private String iconUrl;
    private int id;
    private String linkAddr;
    private int linkType;
    private String linkTypeCn;
    private int moduleId;
    private String picUrl;
    private String programId;
    private String secretKey;
    private int showLocation;
    private String showLocationCn;
    private int showOrder;
    private int state;
    private String thumbnail;
}
