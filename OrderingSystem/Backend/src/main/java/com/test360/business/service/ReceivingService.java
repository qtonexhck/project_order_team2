package com.test360.business.service;

import com.test360.business.dao.ReceivingMapper;
import com.test360.business.model.Criteria;
import com.test360.business.model.Receiving;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * receiving
 * @version 1.0 2016-03-18
 * @powerby hetgyd 
 */
@Service
public class ReceivingService {
    @Autowired
    private ReceivingMapper receivingMapper;

    public int countByExample(Criteria example) {
        int count = this.receivingMapper.countByExample(example);
        return count;
    }

    public Receiving selectByPrimaryKey(Integer id) {
        return this.receivingMapper.selectByPrimaryKey(id);
    }

    public List<Receiving> selectByExample(Criteria example) {
        return this.receivingMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.receivingMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Receiving record) {
        return this.receivingMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Receiving record) {
        return this.receivingMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.receivingMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Receiving record, Criteria example) {
        return this.receivingMapper.updateByExampleSelective(record, example.getCondition());
    }

    public int updateByExample(Receiving record, Criteria example) {
        return this.receivingMapper.updateByExample(record, example.getCondition());
    }

    public int insert(Receiving record) {
        return this.receivingMapper.insert(record);
    }

    public int insertSelective(Receiving record) {
        return this.receivingMapper.insertSelective(record);
    }
}