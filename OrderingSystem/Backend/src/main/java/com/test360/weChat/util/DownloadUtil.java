package com.test360.weChat.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class DownloadUtil {
	
	/**
	 * 从微信服务器上下载图片到本地
	 * @param picUrl
	 * @param picName
	 * @return
	 */
	public static boolean downLoadPicture(String picUrl, String picName){
		try {
			URL url = new URL(picUrl);
			DataInputStream dataInputStream = new DataInputStream(url.openStream());	
			FileOutputStream fileOutputStream = new FileOutputStream(new File(picName));
			
			byte[] buffer = new byte[1024];
			int length;
			while((length = dataInputStream.read(buffer)) > 0) {
				fileOutputStream.write(buffer, 0, length);
			}
			
			dataInputStream.close();  
            fileOutputStream.close();  
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
