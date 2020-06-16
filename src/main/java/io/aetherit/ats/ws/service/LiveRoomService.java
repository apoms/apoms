package io.aetherit.ats.ws.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.aetherit.ats.ws.application.support.FileStorageProperties;
import io.aetherit.ats.ws.exception.FileStorageException;
import io.aetherit.ats.ws.exception.NotEnoughAvailablePointException;
import io.aetherit.ats.ws.model.ATSUser;
import io.aetherit.ats.ws.model.dao.ATSLiveGiftBas;
import io.aetherit.ats.ws.model.dao.ATSLiveGiftRel;
import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSLiveRoomUserHst;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.dao.ATSUserDetail;
import io.aetherit.ats.ws.model.dao.ATSWebrtcServer;
import io.aetherit.ats.ws.model.live.ATSLiveRoomCurrent;
import io.aetherit.ats.ws.model.live.ATSLiveRoomPatch;
import io.aetherit.ats.ws.model.live.ATSLiveRoomRanking;
import io.aetherit.ats.ws.model.live.ATSLiveRoomServer;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.model.type.ATSLiveRoomStatus;
import io.aetherit.ats.ws.repository.LiveRoomRepository;

@Service
public class LiveRoomService {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(LiveRoomService.class);

    private UserService userService;
    private LiveRoomRepository liveRoomRepository;
    private FileStorageProperties fileStorageProperties;

    @Autowired
    public LiveRoomService(LiveRoomRepository liveRoomRepository, 
			    		   UserService userService,
			    		   FileStorageProperties fileStorageProperties) {
        this.userService = userService;
        this.liveRoomRepository = liveRoomRepository;
        this.fileStorageProperties = fileStorageProperties;
    }

    /**
     * live room list
     * @return
     */
    public List<ATSLiveRoomCurrent> getLiveRoomList(ATSLangCode langCd) {
//    	List<ATSLiveRoomServer> liveRoomServerList = new ArrayList<ATSLiveRoomServer>();
    	List<ATSLiveRoomCurrent> liveRoomServerList = new ArrayList<ATSLiveRoomCurrent>();
    	List<ATSLiveRoomRanking> liveRoomList = liveRoomRepository.selectLiveRoomList();
    	int i = 0;
    	for(ATSLiveRoomRanking liveRoom:liveRoomList) {
    		ATSWebrtcServer webrtcServer = getWebrtcServer(liveRoom.getRoomId());
    		ATSUser user = userService.getUser(liveRoom.getUserId());
    		
    		boolean liveStatus = false;
    		
    		if(liveRoom.getStatusCode()==ATSLiveRoomStatus.ONAIR) liveStatus=true;
    		
    		if(webrtcServer==null) {
    			webrtcServer = getWebrtcServer(1);
    		}
    		
    		if(webrtcServer==null) {
    			webrtcServer = getWebrtcServer(0);
    		}
    		
//    		ATSLiveRoomServer liveRoomServer = ATSLiveRoomServer.builder()
//    														    .liveRoom(liveRoom)
//    														    .webrtcServer(webrtcServer)
//    														    .anchor(userService.getUser(liveRoom.getUserId()))
//    														    .build();
    		ATSLiveRoomCurrent liveRoomCurrent = ATSLiveRoomCurrent.builder()
    															   .anchorId(++i)
    															   .nickname(user.getNickName())
    															   .liveCoverUrl(liveRoom.getThumbnailUrl())
    															   .topic(liveRoom.getRoomDesc())
    															   .typeCode(liveRoom.getTypeCode())
    															   .liveStatus(liveStatus)
    															   .roomId(liveRoom.getRoomId())
    															   .userId(liveRoom.getUserId())
    															   .publishTime(liveRoom.getPublishTime())
    															   .build();
    		liveRoomServerList.add(liveRoomCurrent);
    	}
    	return liveRoomServerList;
    }
    
    
    /**
     * live room ranking list
     * @return
     */
    public List<ATSLiveRoomCurrent> getLiveRoomRankingList(ATSLangCode langCd) {
//    	List<ATSLiveRoomServer> liveRoomServerList = new ArrayList<ATSLiveRoomServer>();
    	List<ATSLiveRoomCurrent> liveRoomServerList = new ArrayList<ATSLiveRoomCurrent>();
    	List<ATSLiveRoomRanking> liveRoomList = liveRoomRepository.selectLiveRoomRankingList();
    	int i = 0;
    	for(ATSLiveRoomRanking liveRoom:liveRoomList) {
//    		ATSWebrtcServer webrtcServer = getWebrtcServer(liveRoom.getRoomId());
    		ATSUser user = userService.getUser(liveRoom.getUserId());
    		
    		boolean liveStatus = false;
    		
    		if(liveRoom.getStatusCode()==ATSLiveRoomStatus.ONAIR) liveStatus=true;
    		
//    		if(webrtcServer==null) {
//    			webrtcServer = getWebrtcServer(1);
//    		}
//    		
//    		if(webrtcServer==null) {
//    			webrtcServer = getWebrtcServer(0);
//    		}
    		
//    		ATSLiveRoomServer liveRoomServer = ATSLiveRoomServer.builder()
//    														    .liveRoom(liveRoom)
//    														    .webrtcServer(webrtcServer)
//    														    .anchor(userService.getUser(liveRoom.getUserId()))
//    														    .build();
    		ATSLiveRoomCurrent liveRoomCurrent = ATSLiveRoomCurrent.builder()
    															   .anchorId(++i)
    															   .nickname(user.getNickName())
    															   .liveCoverUrl(liveRoom.getThumbnailUrl())
    															   .topic(liveRoom.getRoomDesc())
    															   .typeCode(liveRoom.getTypeCode())
    															   .liveStatus(liveStatus)
    															   .roomId(liveRoom.getRoomId())
    															   .userId(liveRoom.getUserId())
    															   .publishTime(liveRoom.getPublishTime())
    															   .popularity(liveRoom.getJoinedCount())
    															   .build();
    		liveRoomServerList.add(liveRoomCurrent);
    	}
    	return liveRoomServerList;
    }
    
    
    /**
     * live room by room id or user id
     * @return
     */
    public ATSLiveRoomServer getLiveRoom(HashMap<String,Object> map) {
    	ATSLiveRoom liveRoom = liveRoomRepository.selectLiveRoom(map);
    	
		ATSWebrtcServer webrtcServer = getWebrtcServer(liveRoom.getRoomId());
		
//		if(webrtcServer==null) {
//			webrtcServer = getWebrtcServer(1);
//		}
		
		ATSLiveRoomServer liveRoomServer = ATSLiveRoomServer.builder()
														    .liveRoom(liveRoom)
														    .webrtcServer(webrtcServer)
														    .build();
    	return liveRoomServer;
    }
    
    
    /**
     * insert live room
     * @return
     */
    @Transactional
    public void patchLiveRoom(ATSLiveRoomPatch liveRoom) {
    	if(liveRoom.getStatusCode()!=null)
    		liveRoomRepository.insertLiveRoomState(liveRoom);
    	liveRoomRepository.patchLiveRoom(liveRoom);
    }
    
    
    /**
     * webrtc server info
     * @return
     */
    private ATSWebrtcServer getWebrtcServer(long roomId) {
    	return liveRoomRepository.selectWebrtcServer(roomId);
    }
    
    /**
     * insert server room
     * @return
     */
    public void setServerRoom(ATSServerRoom serverRoom) {
    	liveRoomRepository.insertServerRoom(serverRoom);
    }
    
    /**
     * insert live room user history
     * @return
     */
    public void setLiveRoomAccess(ATSLiveRoomUserHst accessRoomHistory) {
    	liveRoomRepository.insertLiveRoomUserHst(accessRoomHistory);
    }
    
    /**
     * gift list
     * @return
     */
    public List<ATSLiveGiftBas> getGiftList(HashMap<String,Object> map) {
    	return liveRoomRepository.selectGiftList(map);
    }
    
    /**
     * gift list
     * @return
     */
    public int getGiftTotalCount(HashMap<String,Object> map) {
    	return liveRoomRepository.selectGiftTotalCount(map);
    }
    
    /**
     * gift list
     * @return
     */
    public ATSLiveGiftBas getGift(int giftIdx) {
    	return liveRoomRepository.selectGift(giftIdx);
    }
    
    /**
     * donate
     */
    @Transactional
    public void setDonate(ATSLiveGiftRel liveGiftRel) {
    	
    	HashMap<String,Object> map = new HashMap<String,Object>();
    	map.put("roomId", liveGiftRel.getRoomId());
    	
    	ATSLiveGiftBas giftItem = this.getGift(liveGiftRel.getGiftIdx());
    	ATSUserDetail user = userService.getUserDetail(liveGiftRel.getUserId());
    	ATSUserDetail anchor = userService.getUserDetail(liveRoomRepository.selectLiveRoom(map).getUserId());
    	
//    	System.out.println("user.getPointAmt() ============= " + user.getPointAmt());
//    	System.out.println("user.getPointAmt() ============= " + giftItem.getPointPrice());
//    	System.out.println("user.getPointAmt().compareTo(giftItem.getPointPrice()) ============= " + user.getPointAmt().compareTo(giftItem.getPointPrice()));
    	
    	if(user.getPointAmt().compareTo(giftItem.getPointPrice()) < 0) {
    		throw new NotEnoughAvailablePointException(user.getPointAmt().intValue(), giftItem.getPointPrice().intValue());
    	}
    	
    	/**
    	 * user point decrease
    	 */
    	userService.decreaseUserPoint(user.getUserId(), giftItem.getPointPrice().multiply(new BigDecimal(liveGiftRel.getDonateQty())));
    	
    	/**
    	 * Anchor point increase
    	 */
    	userService.increaseUserPoint(anchor.getUserId(), giftItem.getPointPrice().multiply(new BigDecimal(liveGiftRel.getDonateQty())));
    	
    	/**
    	 * gift count increase
    	 */
    	HashMap<String,Object> giftMap = new HashMap<String,Object>();
    	giftMap.put("giftIdx", giftItem.getGiftIdx());
    	giftMap.put("donateQty", liveGiftRel.getDonateQty());
    	
    	liveRoomRepository.increaseGiftCount(giftMap);
    	
    	liveRoomRepository.insertLiveGiftRel(liveGiftRel);
    }
    
    
    /**
	 * 사용자 프로파일 사진 업로드
	 * @param userProfile
	 * @param file
	 */
	public void uploadThumbnailObject(ATSLiveRoom liveRoom, MultipartFile file, String fileName, String thumbnailUrl) {

    	Path fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir(), thumbnailUrl, liveRoom.getUserId()+"")
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            
            Path targetLocation = fileStorageLocation.resolve(fileName);
            
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            try {
//            	liveRoomRepository.updateThumbnailUrl(liveRoom);
            	
            	ATSLiveRoomPatch thumbnailPatch = ATSLiveRoomPatch.builder()
            											 .roomId(liveRoom.getRoomId())
            											 .thumbnailUrl(thumbnailUrl)
            											 .build();
            	
            	patchLiveRoom(thumbnailPatch);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}
    
    
}
