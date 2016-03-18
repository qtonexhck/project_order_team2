package com.test360.business.service;

import com.test360.business.dao.RechargingMapper;
import com.test360.business.model.Criteria;
import com.test360.business.model.Recharging;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * recharging
 * @version 1.0 2016-03-18
 * @powerby hetgyd 
 */
@Service
public class RechargingService {
    @Autowired
    private RechargingMapper rechargingMapper;

    public int countByExample(Criteria example) {
        int count = this.rechargingMapper.countByExample(example);
        return count;
    }

    public Recharging selectByPrimaryKey(Integer id) {
        return this.rechargingMapper.selectByPrimaryKey(id);
    }

    public List<Recharging> selectByExample(Criteria example) {
        return this.rechargingMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.rechargingMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Recharging record) {
        return this.rechargingMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Recharging record) {
        return this.rechargingMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.rechargingMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Recharging record, Criteria example) {
        return this.rechargingMapper.updateByExampleSelective(record, example.getCondition());
    }

    public int updateByExample(Recharging record, Criteria example) {
        return this.rechargingMapper.updateByExample(record, example.getCondition());
    }

    public int insert(Recharging record) {
        return this.rechargingMapper.insert(record);
    }

    public int insertSelective(Recharging record) {
        return this.rechargingMapper.insertSelective(record);
    }
}