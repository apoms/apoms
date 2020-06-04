package io.aetherit.ats.ws.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.dao.ATSWebrtcServer;
import io.aetherit.ats.ws.model.live.ATSLiveRoomServer;
import io.aetherit.ats.ws.model.type.ATSLangCode;
import io.aetherit.ats.ws.repository.LiveRoomRepository;

@Service
public class LiveRoomService {
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(LiveRoomService.class);

    private LiveRoomRepository liveRoomRepository;

    @Autowired
    public LiveRoomService(LiveRoomRepository liveRoomRepository) {
        this.liveRoomRepository = liveRoomRepository;
    }

    /**
     * live room list
     * @return
     */
    public List<ATSLiveRoomServer> getLiveRoomList(ATSLangCode langCd) {
    	List<ATSLiveRoomServer> liveRoomServerList = new ArrayList<ATSLiveRoomServer>();
    	List<ATSLiveRoom> liveRoomList = liveRoomRepository.selectLiveRoomList();
    	for(ATSLiveRoom liveRoom:liveRoomList) {
    		ATSWebrtcServer webrtcServer = getWebrtcServer(liveRoom.getRoomId());
    		
    		if(webrtcServer==null) {
    			webrtcServer = getWebrtcServer(0);
    		}
    		ATSLiveRoomServer liveRoomServer = ATSLiveRoomServer.builder()
    														    .liveRoom(liveRoom)
    														    .webrtcServer(webrtcServer)
    														    .build();
    		liveRoomServerList.add(liveRoomServer);
    	}
    	
    	return liveRoomServerList;
    }
    
    /**
     * insert live room
     * @return
     */
    public void setLiveRoom(ATSLiveRoom liveRoom) {
    	liveRoomRepository.insertLiveRoom(liveRoom);
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
    
}
