package com.test360.weChat.runnable;

import com.test360.weChat.model.AccessToken;
import com.test360.weChat.util.WeChatUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by HZK on 2016/3/21.
 */
public class WeChatMain {
    public static void main(String[] args) {
        AccessToken token = WeChatUtil.getAccessToken();
        System.out.println("token : " + token.getToken());
        System.out.println("validTime : " + token.getExpiresIn());

        String menu = JSONObject.toJSON(WeChatUtil.initMenu()).toString();
        int result = WeChatUtil.createMenu(token.getToken(), menu);
        if(result == 0) {
            System.out.println("创建菜单成功");
        } else {
            System.out.println("创建菜单失败, 错误码 : " + result);
        }

//        String content = "马上就要9点半了,小伙伴们赶紧订餐";
//        String result = WeChatUtil.sendAllMessage(token.getToken(), content);
//        result = WeChatUtil.sendALLMessage_getState(token.getToken(), result);

//        String weChat = WeChatUtil.getUserId("001830fc270283456e082572a301249T");
//        System.out.println(weChat);
    }
}
