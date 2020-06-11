package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSLiveGiftBas;
import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSLiveRoomUserHst;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.dao.ATSUserBas;
import io.aetherit.ats.ws.model.dao.ATSWebrtcServer;
import io.aetherit.ats.ws.model.live.ATSLiveRoomPatch;

public interface LiveRoomMapper {
	List<ATSLiveRoom> selectLiveRoomList();
	ATSLiveRoom selectLiveRoom(HashMap<String,Object> map);
	int patchLiveRoom(ATSLiveRoomPatch liveRoom);
	ATSWebrtcServer selectWebrtcServer(long roomId);
	int insertServerRoom(ATSServerRoom serverRoom);
	int insertLiveRoomUserHst(ATSLiveRoomUserHst accessRoomHistory);
	List<ATSLiveGiftBas> selectGiftList(HashMap<String,Object> map);
}
