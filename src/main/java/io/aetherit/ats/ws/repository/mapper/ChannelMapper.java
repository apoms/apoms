package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSMovieChannelCtg;

public interface ChannelMapper {
	List<ATSMovieChannelCtg> selectDefaultChannelList();
	List<ATSMovieChannelCtg> selectSubChannelList();
	List<ATSMovieChannelCtg> selectSubChannel2List(HashMap<String,Object> map);
}
