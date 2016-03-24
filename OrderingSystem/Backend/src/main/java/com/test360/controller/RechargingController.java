package com.test360.controller;

import com.test360.business.model.Recharging;
import com.test360.business.model.User;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HZK on 2016/3/24.
 */

@Controller
@RequestMapping(value="/recharging")
public class RechargingController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RechargingService rechargingService;
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value="/recharge")
    public Object recharge(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> parames) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        //调用动力加充值接口
        //充值成功
        if(true) {
            Recharging recharging = new Recharging();
            recharging.setUserId((int)parames.get("userId"));
            recharging.setMoney(new BigDecimal(Integer.valueOf((int) parames.get("money"))));
            recharging.setRechargeTime(new Date());
            rechargingService.insert(recharging);

            User user = userService.selectByPrimaryKey((int)parames.get("userId"));
            user.setBalance(user.getBalance().add(recharging.getMoney()));
            userService.updateByPrimaryKey(user);

            map.put("recharging", recharging);
            map.put("success", true);
            map.put("msg", "充值成功");

        } else {
            map.put("success", false);
            map.put("msg", "充值失败");
        }

        return map;
    }
}
