package com.xha.mangotv.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * md5加密的算�?
	 * 
	 * @param password
	 * @return
	 */
	public static String encode(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] result = messageDigest.digest(password.getBytes());
			StringBuffer sb = new StringBuffer();
			for (byte b : result) {
				int number = b & 0xff;// 加盐
				String hex = Integer.toHexString(number);
				if (hex.length() == 1) {
					sb.append("0");
				}
				sb.append(hex);
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	 public final static String MD5(String s) {  
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};         
	        try {  
	            byte[] btInput = s.getBytes();  
	            // 获得MD5摘要算法的 MessageDigest 对象  
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
	            // 使用指定的字节更新摘要  
	            mdInst.update(btInput);  
	            // 获得密文  
	            byte[] md = mdInst.digest();  
	            // 把密文转换成十六进制的字符串形式  
	            int j = md.length;  
	            char str[] = new char[j * 2];  
	            int k = 0;  
	            for (int i = 0; i < j; i++) {  
	                byte byte0 = md[i];  
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
	                str[k++] = hexDigits[byte0 & 0xf];  
	            }  
	            return new String(str);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	//生成MD5  
	    public static String getMD5(String message) {  
	        String md5 = "";  
	        try {  
	            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象  
	            byte[] messageByte = message.getBytes("UTF-8");  
	            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位  
	            md5 = bytesToHex(md5Byte);                            // 转换为16进制字符串  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return md5;  
	    }  
	    
	    // 二进制转十六进制  
	    public static String bytesToHex(byte[] bytes) {  
	        StringBuffer hexStr = new StringBuffer();  
	        int num;  
	        for (int i = 0; i < bytes.length; i++) {  
	            num = bytes[i];  
	             if(num < 0) {  
	                 num += 256;  
	            }  
	            if(num < 16){  
	                hexStr.append("0");  
	            }  
	            hexStr.append(Integer.toHexString(num));  
	        }  
	        return hexStr.toString().toUpperCase();  
	    }  
}
