package io.aetherit.ats.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.aetherit.ats.ws.model.common.ATSAds;
import io.aetherit.ats.ws.model.common.ATSBanner;
import io.aetherit.ats.ws.model.dao.ATSAdvertisiong;
import io.aetherit.ats.ws.repository.AdvertisingRepository;

@Service
public class AdvertisingService {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AdvertisingService.class);

    private AdvertisingRepository advertisingRepository;

    @Autowired
    public AdvertisingService(AdvertisingRepository advertisingRepository) {
        this.advertisingRepository = advertisingRepository;
    }

    /**
     * 배너 목록 조회
     * @param userId
     * @return
     */
    public List<ATSBanner> getBannerList(String langCd) {
    	List<ATSBanner> bannerList = new ArrayList<ATSBanner>();
    	List<ATSAdvertisiong> advertisingList = advertisingRepository.selectAdsList(langCd);
    	
    	for(int i=1;i<=10;i++) {
    		List<ATSAds> ads = new ArrayList<ATSAds>();
    		for(ATSAdvertisiong advertising:advertisingList) {
        		if(advertising.getTypeId()==i) {
        			ATSAds ad = ATSAds.builder()
        						.adName(advertising.getAdName())
        						.adShowTime(advertising.getAdShowTime())
        						.adSkipTime(advertising.getAdSkipTime())
        						.appid(null)
        						.channelType(advertising.getChannelType())
        						.equiCls(4)
        						.equiClsCn(null)
        						.gameType(null)
        						.iconUrl(null)
        						.id(advertising.getId())
        						.linkAddr(advertising.getLinkAddr())
        						.linkType(advertising.getLinkType())
        						.linkTypeCn(advertising.getLinkTypeCn())
        						.moduleId(advertising.getModuleId())
        						.picUrl(null)
        						.programId(null)
        						.secretKey(null)
        						.showLocation(advertising.getShowLocation())
        						.showLocationCn(advertising.getShowLocationCn())
        						.showOrder(advertising.getShowOrder())
        						.state(advertising.getState())
        						.thumbnail(advertising.getThumbnail())
        						.build();
        			ads.add(ad);
        		}
        	}
    		ATSBanner banner = ATSBanner.builder()
    						   .typeid(i)
    						   .ads(ads)
    						   .build();
    		bannerList.add(banner);
    	}
        return bannerList;
    }
    
    
    
}
