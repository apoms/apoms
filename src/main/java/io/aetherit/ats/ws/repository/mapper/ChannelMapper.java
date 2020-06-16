package io.aetherit.ats.ws.repository.mapper;

import java.util.HashMap;
import java.util.List;

import io.aetherit.ats.ws.model.dao.ATSMovieChannelCtg;

public interface ChannelMapper {
	List<ATSMovieChannelCtg> selectDefaultChannelList();
	List<ATSMovieChannelCtg> selectChannelList();
	List<ATSMovieChannelCtg> selectSubChannelList(HashMap<String,Object> map);
}
