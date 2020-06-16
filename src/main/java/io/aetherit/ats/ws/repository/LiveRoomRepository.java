package io.aetherit.ats.ws.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.dao.ATSLiveGiftBas;
import io.aetherit.ats.ws.model.dao.ATSLiveGiftRel;
import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSLiveRoomUserHst;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.dao.ATSWebrtcServer;
import io.aetherit.ats.ws.model.live.ATSLiveRoomPatch;
import io.aetherit.ats.ws.model.live.ATSLiveRoomRanking;
import io.aetherit.ats.ws.repository.mapper.LiveRoomMapper;

@Repository
public class LiveRoomRepository {
    private LiveRoomMapper liveRoomMapper;

    @Autowired
    public LiveRoomRepository(LiveRoomMapper liveRoomMapper) {
        this.liveRoomMapper = liveRoomMapper;
    }

    public List<ATSLiveRoomRanking> selectLiveRoomList() {
        return liveRoomMapper.selectLiveRoomList();
    }
    
    public List<ATSLiveRoomRanking> selectLiveRoomRankingList() {
    	return liveRoomMapper.selectLiveRoomRankingList();
    }
    
    public ATSLiveRoom selectLiveRoom(HashMap<String,Object> map) {
    	return liveRoomMapper.selectLiveRoom(map);
    }
    
    public int insertLiveRoomState(ATSLiveRoomPatch liveRoom) {
    	return liveRoomMapper.insertLiveRoomState(liveRoom);
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
    
    public int selectGiftTotalCount(HashMap<String,Object> map) {
    	return liveRoomMapper.selectGiftTotalCount(map);
    }
    
    public ATSLiveGiftBas selectGift(int giftIdx) {
    	return liveRoomMapper.selectGift(giftIdx);
    }
    
    public int insertLiveGiftRel(ATSLiveGiftRel liveGiftRel) {
    	return liveRoomMapper.insertLiveGiftRel(liveGiftRel);
    }
    
    public int increaseGiftCount(HashMap<String,Object> map) {
    	return liveRoomMapper.increaseGiftCount(map);
    }
    
}



