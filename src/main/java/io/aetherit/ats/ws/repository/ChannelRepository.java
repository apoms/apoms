package io.aetherit.ats.ws.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.dao.ATSMovieChannelCtg;
import io.aetherit.ats.ws.repository.mapper.ChannelMapper;

@Repository
public class ChannelRepository {
    private ChannelMapper channelMapper;

    @Autowired
    public ChannelRepository(ChannelMapper channelMapper) {
        this.channelMapper = channelMapper;
    }

    public List<ATSMovieChannelCtg> selectDefaultChannelList() {
        return channelMapper.selectDefaultChannelList();
    }
    
    public List<ATSMovieChannelCtg> selectSubChannelList() {
    	return channelMapper.selectSubChannelList();
    }
    
    public List<ATSMovieChannelCtg> selectSubChannel2List(HashMap<String,Object> map) {
    	return channelMapper.selectSubChannel2List(map);
    }
}



