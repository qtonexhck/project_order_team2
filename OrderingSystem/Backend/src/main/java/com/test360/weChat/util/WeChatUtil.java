package com.test360.weChat.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import com.test360.weChat.model.AccessToken;
import com.test360.weChat.model.menu.Button;
import com.test360.weChat.model.menu.ClickButton;
import com.test360.weChat.model.menu.Menu;
import com.test360.weChat.model.menu.ViewButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.View;

public class WeChatUtil {
	private final static String APPID = "wx037785e9a1c37291";
	private final static String APPSECRET = "2097d91254741ed20ebafa5d812e35e1";
	
	private final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private final static String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	private final static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	private final static String GET_USERDATA_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
	private final static String GET_USERDATA_AT_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	private final static String SEND_ALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
	private final static String SEND_ALL_STATE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN";

	private final static String SEND_ALL_TEXT_MESSAGE = "{\"filter\":{\"is_to_all\":false,\"group_id\":2},\"text\":{\"content\":\"CONTENT\"},\"msgtype\":\"text\"}";
	private final static String SEND_ALL_STATE_MESSAGE = "{\"msg_id\": \"MSG_ID\"}";
	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public static JSONObject doGetStr(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				String result = EntityUtils.toString(entity, "UTF-8");
				jsonObject = JSONObject.parseObject(result);
			}
		} catch(ClientProtocolException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * post请求
	 * @param url
	 * @param outStr
	 * @return
	 */
	public static JSONObject doPostStr(String url, String outStr) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpPost.setEntity(new StringEntity(outStr, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonObject = JSONObject.parseObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * 获取access_token
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getIntValue("expires_in"));
		}
		return token;
	}
	
	/**
	 * 初始化菜单
	 * @return
	 */
	public static Menu initMenu() {

		//一级菜单 : 点餐
		ViewButton orderButton = new ViewButton("点餐", "view", null,
				GET_USERDATA_CODE_URL.replace("APPID", APPID).replace("REDIRECT_URI", "http://soloist.ngrok.natapp.cn/Frontend/#/order"));
		//一级菜单 : 交易, 包含两个二级菜单(充值/查询)
		ViewButton rechargeButton = new ViewButton("充值", "view", null,
				GET_USERDATA_CODE_URL.replace("APPID", APPID).replace("REDIRECT_URI", "http://soloist.ngrok.natapp.cn/Frontend/#/recharge"));
		ViewButton queryButton = new ViewButton("查询", "view", null,
				GET_USERDATA_CODE_URL.replace("APPID", APPID).replace("REDIRECT_URI", "http://soloist.ngrok.natapp.cn/Frontend/#/queryLog"));
		Button businessButton = new Button("交易", null, new Button[]{rechargeButton, queryButton});
		//一级菜单 : 个人中心, 包含两个二级菜单(解除绑定/修改手机号)
		ViewButton removeButton = new ViewButton("解除绑定", "view", null,
				GET_USERDATA_CODE_URL.replace("APPID", APPID).replace("REDIRECT_URI", "http://soloist.ngrok.natapp.cn/Frontend/#/unbind"));
		ViewButton modifyButton = new ViewButton("修改手机号", "view", null,
				GET_USERDATA_CODE_URL.replace("APPID", APPID).replace("REDIRECT_URI", "http://soloist.ngrok.natapp.cn/Frontend/#/modifyPhone"));
		Button userCenterButton = new Button("个人中心", null, new Button[]{removeButton, modifyButton});
		//主菜单 : 包含上述三个一级菜单
		Menu menu = new Menu(new Button[]{orderButton, businessButton, userCenterButton});
		return menu;
	}
	
	/**
	 * 创建菜单
	 * @param token
	 * @param menu
	 * @return
	 */
	public static int createMenu(String token, String menu) {
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject != null) {
			result = jsonObject.getIntValue("errcode");
		}
		return result;
	}

	/**
	 * 获取用户的详细信息
	 * @param code
	 * @return
     */
	public static String getUserId(String code) {
		String userId = "";
		String url = GET_USERDATA_AT_URL.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code);
		com.alibaba.fastjson.JSONObject jsonObject = doGetStr(url);
		System.out.println(jsonObject.toString());
		if(jsonObject != null) {
			userId = jsonObject.getString("openid");
		}
		return userId;
	}

	/**
	 * 群发文本信息到关注的用户
	 * @param content
	 * @return
     */
	public static String sendAllMessage(String token, String content) {
		String result = null;
		String url = SEND_ALL_URL.replace("ACCESS_TOKEN", token);
		content = SEND_ALL_TEXT_MESSAGE.replace("CONTENT", content);
		JSONObject jsonObject = doPostStr(url, content);
		if(jsonObject != null) {
//			System.out.println(jsonObject.toString());
			result = jsonObject.getString("msg_id");
		}
		return result;
	}

	public static String sendALLMessage_getState(String token, String msgId) {
		String result = null;
		String url = SEND_ALL_STATE_URL.replace("ACCESS_TOKEN", token);
		msgId = SEND_ALL_STATE_MESSAGE.replace("MSG_ID", msgId);
		JSONObject jsonObject = doPostStr(url, msgId);
		if(jsonObject != null) {
//			System.out.println(jsonObject.toString());
			result = jsonObject.getString("msg_status");
		}
		return result;
	}
}
