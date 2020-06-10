package io.aetherit.ats.ws.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.aetherit.ats.ws.model.dao.ATSMovieChannelCtg;
import io.aetherit.ats.ws.repository.mapper.AdminMapper;

@Repository
public class AdminRepository {
    private AdminMapper adminMapper;

    @Autowired
    public AdminRepository(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public int approvalUserTypeAnchor(HashMap<String,Object> map) {
        return adminMapper.approvalUserTypeAnchor(map);
    }
}



