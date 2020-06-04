package io.aetherit.ats.ws.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.dao.ATSWebrtcServer;
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
    
    public int insertLiveRoom(ATSLiveRoom liveRoom) {
    	return liveRoomMapper.insertLiveRoom(liveRoom);
    }
    
    public ATSWebrtcServer selectWebrtcServer(long roomId) {
    	return liveRoomMapper.selectWebrtcServer(roomId);
    }
    
    public int insertServerRoom(ATSServerRoom serverRoom) {
    	return liveRoomMapper.insertServerRoom(serverRoom);
    }
    
}



