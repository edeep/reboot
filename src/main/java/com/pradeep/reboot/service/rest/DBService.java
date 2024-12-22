package com.pradeep.reboot.service.rest;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pradeep.reboot.entity.RebootData;
import com.pradeep.reboot.repo.RebootDataRepository;

import java.util.List;

@Service
public class DBService {
    @Autowired
    private RebootDataRepository rebootDataRepository;

    public List<RebootData> getAllRebootData() {
        return rebootDataRepository.findAll();
    }

    public RebootData getRebootById(int id) {
        return rebootDataRepository.findById(id);
    }

    public int addRebootData(RebootData rebootData) {
        return rebootDataRepository.save(rebootData);
    }

    public int updateRebootData(RebootData rebootData) {
        return rebootDataRepository.update(rebootData);
    }

    public int deleteRebootDataById(int id) {
        return rebootDataRepository.deleteById(id);
    }
}
