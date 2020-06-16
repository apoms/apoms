package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSLiveGiftBas;
import io.aetherit.ats.ws.model.dao.ATSLiveGiftRel;
import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSLiveRoomUserHst;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.dao.ATSWebrtcServer;
import io.aetherit.ats.ws.model.live.ATSLiveRoomPatch;
import io.aetherit.ats.ws.model.live.ATSLiveRoomRanking;

public interface LiveRoomMapper {
	List<ATSLiveRoomRanking> selectLiveRoomList();
	List<ATSLiveRoomRanking> selectLiveRoomRankingList();
	ATSLiveRoom selectLiveRoom(HashMap<String,Object> map);
	int insertLiveRoomState(ATSLiveRoomPatch liveRoom);
	int patchLiveRoom(ATSLiveRoomPatch liveRoom);
	ATSWebrtcServer selectWebrtcServer(long roomId);
	int insertServerRoom(ATSServerRoom serverRoom);
	int insertLiveRoomUserHst(ATSLiveRoomUserHst accessRoomHistory);
	List<ATSLiveGiftBas> selectGiftList(HashMap<String,Object> map);
	int selectGiftTotalCount(HashMap<String,Object> map);
	ATSLiveGiftBas selectGift(int giftIdx);
	int insertLiveGiftRel(ATSLiveGiftRel liveGiftRel);
	int increaseGiftCount(HashMap<String,Object> map);
}
