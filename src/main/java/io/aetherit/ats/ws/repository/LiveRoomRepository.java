package io.aetherit.ats.ws.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.dao.ATSLiveGiftBas;
import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSLiveRoomUserHst;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.dao.ATSUserBas;
import io.aetherit.ats.ws.model.dao.ATSWebrtcServer;
import io.aetherit.ats.ws.model.live.ATSLiveRoomPatch;
import io.aetherit.ats.ws.repository.mapper.LiveRoomMapper;

@Repository
public class LiveRoomRepository {
    private LiveRoomMapper liveRoomMapper;

    @Autowired
    public LiveRoomRepository(LiveRoomMapper liveRoomMapper) {
        this.liveRoomMapper = liveRoomMapper;
    }

    public List<ATSLiveRoom> selectLiveRoomList() {
        return liveRoomMapper.selectLiveRoomList();
    }
    
    public ATSLiveRoom selectLiveRoom(HashMap<String,Object> map) {
    	return liveRoomMapper.selectLiveRoom(map);
    }
    
    public int patchLiveRoom(ATSLiveRoomPatch liveRoom) {
    	return liveRoomMapper.patchLiveRoom(liveRoom);
    }
    
    public ATSWebrtcServer selectWebrtcServer(long roomId) {
    	return liveRoomMapper.selectWebrtcServer(roomId);
    }
    
    public int insertServerRoom(ATSServerRoom serverRoom) {
    	return liveRoomMapper.insertServerRoom(serverRoom);
    }
    
    public int insertLiveRoomUserHst(ATSLiveRoomUserHst accessRoomHistory) {
    	return liveRoomMapper.insertLiveRoomUserHst(accessRoomHistory);
    }
    
    public List<ATSLiveGiftBas> selectGiftList(HashMap<String,Object> map) {
    	return liveRoomMapper.selectGiftList(map);
    }
    
}



