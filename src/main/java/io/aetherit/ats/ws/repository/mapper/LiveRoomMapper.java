package io.aetherit.ats.ws.repository.mapper;

import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSLiveRoom;
import io.aetherit.ats.ws.model.dao.ATSServerRoom;
import io.aetherit.ats.ws.model.dao.ATSWebrtcServer;

public interface LiveRoomMapper {
	List<ATSLiveRoom> selectLiveRoomList();
	int insertLiveRoom(ATSLiveRoom liveRoom);
	ATSWebrtcServer selectWebrtcServer(long roomId);
	int insertServerRoom(ATSServerRoom serverRoom);
}
