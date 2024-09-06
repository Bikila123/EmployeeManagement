package com.Internship.Backend.services;

import com.Internship.Backend.mappers.AuditTrailMapper;
import com.Internship.Backend.models.AuditTrailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditTrailService {

    @Autowired
    private AuditTrailMapper mapper;

//    public List<AuditTrailModel> fetchAuditTrails(CommissionFilterModel model) {
//        return mapper.fetchAuditTrails(model);
//    }
}
