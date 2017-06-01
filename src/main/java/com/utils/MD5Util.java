/**   
* @Title: MD5Util.java 
* @Package com.unicdata.until 
* @Description:上海海略管理信息咨询有限公司 Haistand Consulting Company 版权所有
*/
package com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	/** 
	 * @ClassName: MD5Util 
	 * @Description: 
	 * @author 杨树楷
	 * @date 2015年12月15日 下午4:21:07 
	 * 
	 */
	
  
    /** 
     * MD5加密 
     *  
     * @param password 
     * @return 
     */  
    public static String encryptMD5(String password) {  
  
        MessageDigest md = null;  
        String encryptPwd = null;  
  
        try {  
            md = MessageDigest.getInstance("MD5");  
            
            byte[] digest = md.digest(password.getBytes());  
            encryptPwd = byteToStr(digest);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
  
        return encryptPwd ;  
    }  
  
    /** 
     * 将字节数组转换为十六进制字符串 
     *  
     * @param byteArray 
     * @return 
     */  
    private static String byteToStr(byte[] byteArray) {  
        String strDigest = "";  
        for (int i = 0; i < byteArray.length; i++) {  
            strDigest += byteToHexStr(byteArray[i]);  
        }  
        return strDigest;  
    }  
  
    /** 
     * 将字节转换为十六进制字符串 
     *  
     * @param mByte 
     * @return 
     */  
    private static String byteToHexStr(byte mByte) {  
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
        char[] tempArr = new char[2];  
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];  
        tempArr[1] = Digit[mByte & 0X0F];  
  
        String s = new String(tempArr);  
        return s;  
    } 
    
    public static void main(String[] args){
    	String result=encryptMD5("insertMemberUser");
    	System.out.println(result);
    }
    
}
