package com.test360.weChat.util;

import java.security.MessageDigest;
import java.util.Arrays;

public class CheckUtil {
	private static final String token = "kaifanla";
	
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] arr = new String[]{token, timestamp, nonce};
		System.out.println("arr : " + arr[0] + arr[1] + arr[2]);
		//排序
		Arrays.sort(arr);
		//生成字符串
		StringBuffer content = new StringBuffer();
		for(String temp : arr) {
			content.append(temp);
		}
		
		//SHA1加密
		String temp = getSHA1(content.toString());
		
		return temp.equals(signature);
	}
	
	/**
	 * SHA1加密算法
	 * @param str
	 * @return
	 */
	public static String getSHA1(String str) {
		if(str == null || str.length() == 0) {
			return null;
		}
		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for(int i = 0; i < j; ++i) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
