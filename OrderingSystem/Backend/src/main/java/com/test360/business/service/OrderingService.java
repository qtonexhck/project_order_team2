package com.test360.business.service;

import com.test360.business.dao.OrderingMapper;
import com.test360.business.model.Criteria;
import com.test360.business.model.Ordering;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ordering
 * @version 1.0 2016-03-18
 * @powerby hetgyd 
 */
@Service
public class OrderingService {
    @Autowired
    private OrderingMapper orderingMapper;

    public int countByExample(Criteria example) {
        int count = this.orderingMapper.countByExample(example);
        return count;
    }

    public Ordering selectByPrimaryKey(Integer id) {
        return this.orderingMapper.selectByPrimaryKey(id);
    }

    public List<Ordering> selectByExample(Criteria example) {
        return this.orderingMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.orderingMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Ordering record) {
        return this.orderingMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Ordering record) {
        return this.orderingMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.orderingMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Ordering record, Criteria example) {
        return this.orderingMapper.updateByExampleSelective(record, example.getCondition());
    }

    public int updateByExample(Ordering record, Criteria example) {
        return this.orderingMapper.updateByExample(record, example.getCondition());
    }

    public int insert(Ordering record) {
        return this.orderingMapper.insert(record);
    }

    public int insertSelective(Ordering record) {
        return this.orderingMapper.insertSelective(record);
    }
}