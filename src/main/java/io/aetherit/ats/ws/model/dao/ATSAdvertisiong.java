package io.aetherit.ats.ws.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSAdvertisiong {
	private int id;
    private String adName;
    private int adShowTime;
    private int adSkipTime;
    private int adWeight;
    private String channelType;
    
    private int typeId;
    private String linkAddr;
    private int linkType;
    private String linkTypeCn;
    private int moduleId;
    private int showLocation;
    private String showLocationCn;
    private int showOrder;
    private int state;
    private String thumbnail;
}
