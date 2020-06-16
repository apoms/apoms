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
    
    public List<ATSMovieChannelCtg> selectChannelList() {
    	return channelMapper.selectChannelList();
    }
    
    public List<ATSMovieChannelCtg> selectSubChannelList(HashMap<String,Object> map) {
    	return channelMapper.selectSubChannelList(map);
    }
    
}



