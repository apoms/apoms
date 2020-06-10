package io.aetherit.ats.ws.model.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSDomain {
    private List<String> backUpBlogDomain;
    private List<String> backUpDomain;
    private List<String> backUpIP;
    private List<String> h5Url;
    private List<String> patApiDomain;
    private List<String> patImgDownUrl;
    private List<String> patImgUpUrl;
    private List<String> patchDownload;
    private List<String> pictureUpload;
    private List<String> server;
    private List<String> shareDomain;
    private List<String> userCoverDomain;
    private List<String> userUpVedioDomain;
    private List<String> videoCover;
    private List<String> videoUpLoad;
    private List<String> viewPicture;
    private List<String> viewVideo;
    private List<String> viewVideo2;
    private List<Integer> viewVideoWeight;
    private List<Integer> viewVideoWeight2;
}
