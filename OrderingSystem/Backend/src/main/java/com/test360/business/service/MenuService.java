package com.test360.business.service;

import com.test360.business.dao.MenuMapper;
import com.test360.business.model.Criteria;
import com.test360.business.model.Menu;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * menu
 * @version 1.0 2016-03-18
 * @powerby hetgyd 
 */
@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    public int countByExample(Criteria example) {
        int count = this.menuMapper.countByExample(example);
        return count;
    }

    public Menu selectByPrimaryKey(Integer id) {
        return this.menuMapper.selectByPrimaryKey(id);
    }

    public List<Menu> selectByExample(Criteria example) {
        return this.menuMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(Integer id) {
        return this.menuMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Menu record) {
        return this.menuMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Menu record) {
        return this.menuMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(Criteria example) {
        return this.menuMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(Menu record, Criteria example) {
        return this.menuMapper.updateByExampleSelective(record, example.getCondition());
    }

    public int updateByExample(Menu record, Criteria example) {
        return this.menuMapper.updateByExample(record, example.getCondition());
    }

    public int insert(Menu record) {
        return this.menuMapper.insert(record);
    }

    public int insertSelective(Menu record) {
        return this.menuMapper.insertSelective(record);
    }
}