package io.aetherit.ats.ws.model.dao;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSDomainListBas {
	private int domainIdx;
	private String backupBlogDomain;
	private String backupDomain;
	private String backupIp;
	private String h5Url;
    private String patApiDomain;
    private String patImgDownUrl;
    private String patImgUpUrl;
    private String patchDownload;
    private String pictureUpload;
    private String server;
    private String shareDomain;
    private String userCoverDomain;
    private String userUpVedioDomain;
    private String videoCover;
    private String videoUpload;
    private String viewPicture;
    
    private String viewVideo;
    private String viewVideo2;
    private String viewVideoWeight;
    private String viewVideoWeight2;
    
    private String regId;
    private LocalDateTime regDt;
    private String modId;
    private LocalDateTime modDt;
}

