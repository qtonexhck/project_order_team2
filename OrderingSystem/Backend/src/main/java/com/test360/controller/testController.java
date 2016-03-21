package com.test360.controller;

import com.test360.business.model.User;
import com.test360.business.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HZK on 2016/3/18.
 */

@Controller
@RequestMapping(value = "/test")
public class testController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login")
    public Object login(HttpServletRequest httpServletRequest, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        User user = userService.selectByPrimaryKey(1);
        System.out.println("OrderingSystemUser : " + user);

        logger.info(parames.toString());
        map.put("success", true);
        map.put("msg", "登录成功");
        map.put("user", user);

        return map;
    }

    @ResponseBody
    @RequestMapping(value="/test")
    public Object test(HttpServletRequest httpServletRequest, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        logger.info(parames.toString());
        map.put("successs", true);
        map.put("msg", "测试成功");

        return map;
    }
}
