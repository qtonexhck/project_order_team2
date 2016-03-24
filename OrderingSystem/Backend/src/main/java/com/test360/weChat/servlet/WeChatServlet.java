package com.test360.weChat.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test360.weChat.model.message.News;
import com.test360.weChat.model.message.NewsMessage;
import com.test360.weChat.model.message.TextMessage;
import com.test360.weChat.util.CheckUtil;
import com.test360.weChat.util.MessageUtil;

import com.test360.weChat.util.WeChatUtil;
import org.apache.zookeeper.server.quorum.QuorumCnxManager;
import org.dom4j.DocumentException;

public class WeChatServlet extends HttpServlet {
	private static final long serialVersionUID = 2579496639168759497L;
	private final static String APPID = "wx037785e9a1c37291";
	private final static String GET_USERDATA_CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";


	public WeChatServlet() {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		//校验成功,确认请求来自微信服务器
		if(CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			//回复消息载体
			String message = null;

			//消息类型 : 事件
			if(MessageUtil.MESSAGE_EVENT.equals(msgType)) {
				String eventType = map.get("Event");

				//事件类型 : 关注
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					News news = new News("欢迎关注'开饭啦@全通'公众号", "欢迎关注'开饭啦@全通'公众号",
							"http://soloist.ngrok.natapp.cn/OrderingSystem/image/subscribe.png",
							GET_USERDATA_CODE_URL.replace("APPID", APPID).replace("REDIRECT_URI", "http://soloist.ngrok.natapp.cn/Frontend/#/order"));
					List<News> newsList = new ArrayList<News>();
					newsList.add(news);
					NewsMessage newsMessage = new NewsMessage(fromUserName, toUserName, new Date().getTime(),
							MessageUtil.MESSAGE_NEWS, newsList.size(), newsList);
					message = MessageUtil.newsMessageToXml(newsMessage);

//					TextMessage subscribeMessage = new TextMessage(fromUserName, toUserName, new Date().getTime(),
//							MessageUtil.MESSAGE_TEXT, "欢迎关注!", null);
//					message = MessageUtil.textMessageToXml(subscribeMessage);

					System.out.println("关注推送消息 : \n" + message);
				}
			}

			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
