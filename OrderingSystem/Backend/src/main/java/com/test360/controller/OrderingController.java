package com.test360.controller;

import com.test360.business.model.Criteria;
import com.test360.business.model.Ordering;
import com.test360.business.model.User;
import com.test360.business.service.OrderingService;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HZK on 2016/3/22.
 */

@Controller
@RequestMapping(value="/ordering")
public class OrderingController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    OrderingService orderingService;
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value="/order")
    public Object order(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        Ordering ordering = new Ordering();
        ordering.setUserId((int)parames.get("userId"));
        ordering.setOrderTime(new Date());
        ordering.setMenuType((String)parames.get("menu_type"));
        ordering.setLocation((String)parames.get("location"));
        ordering.setPrice(new BigDecimal(Integer.valueOf((int)parames.get("price"))));
        ordering.setIsTaken((byte)0);

        if(orderingService.insert(ordering) != 0) {
            Criteria criteria = new Criteria();
            criteria.getCondition().put("userId", ordering.getUserId());
            criteria.getCondition().put("orderTime", ordering.getOrderTime());
            ordering = orderingService.selectByExample(criteria).get(0);

            map.put("success", true);
            map.put("msg", "订单提交成功");
            map.put("ordering", ordering);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/pay")
    public Object pay(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Ordering ordering = orderingService.selectByPrimaryKey((int)parames.get("orderingId"));
        User user = userService.selectByPrimaryKey((int)parames.get("userId"));

        //调用动力加支付接口
        //支付成功
        if(true) {
            user.setBalance(user.getBalance().subtract(ordering.getPrice()));
            userService.updateByPrimaryKey(user);
            ordering.setIsTaken((byte)1);
            orderingService.updateByPrimaryKey(ordering);
            map.put("user", user);
            map.put("ordering", ordering);
            map.put("success", true);
            map.put("msg", "支付成功");
        } else {
            map.put("success", false);
            map.put("msg", "支付失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/cancelPay")
    public Object cancelPay(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames){
        HashMap<String, Object> map = new HashMap<String, Object>();

        orderingService.deleteByPrimaryKey((int)parames.get("orderingId"));
        map.put("success", true);
        map.put("msg", "删除订单成功");

        return map;
    }

    @ResponseBody
    @RequestMapping(value="/modifyOrder")
    public Object modifyOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Ordering ordering = orderingService.selectByPrimaryKey((int)parames.get("orderingId"));
        ordering.setMenuType((String)parames.get("menuType"));
        ordering.setLocation((String)parames.get("location"));
        ordering.setPrice(new BigDecimal(Integer.valueOf((int)parames.get("price"))));
        orderingService.updateByPrimaryKeySelective(ordering);
        map.put("ordering",ordering);
        map.put("success", true);
        map.put("msg", "订单修改成功");
        return map;
    }
}
