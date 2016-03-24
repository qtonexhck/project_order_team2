package com.test360.controller;

import com.test360.business.model.Criteria;
import com.test360.business.model.Menu;
import com.test360.business.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HZK on 2016/3/22.
 */

@Controller
@RequestMapping(value="/menu")
public class MenuController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MenuService menuService;

    @ResponseBody
    @RequestMapping(value="/getMenu")
    public Object getMenu(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        Criteria criteria = new Criteria();
        criteria.setOrderByClause("menu_type");
        criteria.getCondition().put("menuDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        List<Menu> menuList = menuService.selectByExample(criteria);
        for(int i = 0; i < menuList.size(); ++i) {
            map.put("menu" + (char)(65 + i), menuList.get(i));
            System.out.println(menuList.get(i));
        }
        map.put("success", true);
        map.put("msg", "查找菜单成功");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/getMenuContent")
    public Object getMenuContent(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Criteria criteria = new Criteria();
        criteria.getCondition().put("menuType", parames.get("menu_type"));
        criteria.getCondition().put("menuDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Menu menu = menuService.selectByExample(criteria).get(0);
        map.put("menu", menu);
        map.put("success", true);
        map.put("msg", "查找菜单成功");
        return map;
    }
}
