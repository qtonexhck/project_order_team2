package com.test360.controller;

import com.test360.business.model.Criteria;
import com.test360.business.model.Ordering;
import com.test360.business.model.User;
import com.test360.business.service.OrderingService;
import com.test360.business.service.UserService;
import com.test360.weChat.util.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HZK on 2016/3/22.
 */

@Controller
@RequestMapping(value="/userCenter")
public class UserCenterController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;
    @Autowired
    OrderingService orderingService;

    private String identityingCode =null;

    @ResponseBody
    @RequestMapping(value="/getWeChat")
    public Object getWeChat(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        User user = null;
        Ordering ordering = null;
        HashMap<String, Object> map = new HashMap<String, Object>();
        System.out.println("code : " + (String)parames.get("code"));
        String weChat = WeChatUtil.getUserId((String)parames.get("code"));
        System.out.println("weChat : " + weChat);
        Criteria criteria = new Criteria();
        criteria.getCondition().put("wechat", weChat);
        List<User> userList = userService.selectByExample(criteria);
        if(userList.size() != 0) {
            user = userList.get(0);
            System.out.println("User : " + user);

            criteria.getCondition().clear();
            criteria.put("userId", user.getId());
            criteria.put("orderTime1", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            List<Ordering> orderingList = orderingService.selectByExample(criteria);
            if(orderingList.size() != 0) {
                ordering = orderingList.get(0);
            }
            System.out.println("Ordering : " + ordering);
        }
        map.put("weChat", weChat);
        map.put("user", user);
        map.put("ordering", ordering);
        map.put("success", true);
        map.put("msg", "获取用户信息成功");

        return map;
    }

    @ResponseBody
    @RequestMapping(value="/getIdentityingCode")
    public Object getIdentityingCode(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String phone = (String)parames.get("phone");

        //调用短信验证码接口
        identityingCode = "4567";

        map.put("identityingCode", identityingCode);
        map.put("success", true);
        map.put("msg", "获取验证码成功");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public Object login(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        System.out.println((String)parames.get("weChat"));
        Criteria criteria = new Criteria();
        criteria.getCondition().put("phone", (String)parames.get("phone"));
        User user = userService.selectByExample(criteria).get(0);
        user.setWechat((String)parames.get("weChat"));
        userService.updateByPrimaryKey(user);

        map.put("user", user);
        map.put("success", true);
        map.put("msg", "登录成功");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/unbind")
    public Object unbind(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        int id = (int)parames.get("id");
        User user = userService.selectByPrimaryKey(id);
        user.setWechat("");
        userService.updateByPrimaryKey(user);

        map.put("success", true);
        map.put("msg", "解绑成功");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/modifyPhone")
    public Object modifyPhone(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        User user = userService.selectByPrimaryKey((int)parames.get("userId"));

        Criteria criteria = new Criteria();
        criteria.getCondition().put("phone", parames.get("phone"));
        if(userService.selectByExample(criteria).size() > 0) {
            map.put("success", false);
            map.put("msg", "该手机号已经被注册");
        } else {
            user.setPhone((String)parames.get("phone"));
            userService.updateByPrimaryKey(user);

            map.put("user", user);
            map.put("success", true);
            map.put("msg", "手机修改成功");
        }
        return map;
    }
}
