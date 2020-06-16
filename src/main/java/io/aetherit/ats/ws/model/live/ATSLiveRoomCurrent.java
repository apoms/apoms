package io.aetherit.ats.ws.model.live;

import io.aetherit.ats.ws.model.type.ATSLiveRoomType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ATSLiveRoomCurrent {
	private int anchorId;
	private String nickname;
	private String topic;
	private String liveCoverUrl;
	private ATSLiveRoomType typeCode;
	private boolean liveStatus;
	private int popularity;
	private int publishTime;
	private long roomId;
	private long userId;
}


//anchorId: 1,
//avatar: "",
//id: 001,
//liveCoverUrl: "https://nms.aetherdemo.tk:3001/images/thumbnail/thumbnail1.jpg",
//topic: "topic 1",
//nickname: "aether",
//popularity: 1234,
//markerUrl: "",
//liveStatus: 1,
//herald: "aether",
//publishTime: "",
//userId: '1000000005',
//roomId: '1000000005',