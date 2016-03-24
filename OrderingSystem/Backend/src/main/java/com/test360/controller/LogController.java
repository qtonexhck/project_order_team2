package com.test360.controller;

import com.test360.business.model.*;
import com.test360.business.service.OrderingService;
import com.test360.business.service.ReceivingService;
import com.test360.business.service.RechargingService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by HZK on 2016/3/22.
 */

@Controller
@RequestMapping(value="/LogCenter")
public class LogController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RechargingService rechargingService;
    @Autowired
    OrderingService orderingService;


    @ResponseBody
    @RequestMapping(value = "/queryLog")
    public Object queryLog(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        int userid = (int)parames.get("id");
        //month
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        Date d = new Date(year-1900, month, 1);
        if(d!=null) {
            System.out.println(new SimpleDateFormat("YYYY-MM-DD").format(d));
        }
        Criteria criteria1 = new Criteria();
        criteria1.put("userId", userid);
        criteria1.put("rechargeTime1",d);
        List<Recharging> rechargingList = rechargingService.selectByExample(criteria1);
        map.put("recharge",rechargingList);

        Criteria criteria2 = new Criteria();
        criteria2.put("userId", userid);
        criteria2.put("orderTime1",d);
        List<Ordering> orderingList = orderingService.selectByExample(criteria2);
        map.put("ordering",orderingList);

        map.put("yearmonth",year+"年"+(month+1)+"月");

        map.put("success", true);
        map.put("msg", "登录成功");

        return map;
    }

}