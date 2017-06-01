package com.utils;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONObject;
import com.web.entity.Configure;

public class getAccessTokenUtil {
	public static String getAccessToken(){
		String accesstokenURL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+Configure.WXAppid+"&secret="+Configure.AppSecret;	
		String accessTokenInfo=HttpUtil.getUrl(accesstokenURL);
		try {
			accessTokenInfo = new String(accessTokenInfo.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    JSONObject tokenInfo=JSONObject.parseObject(accessTokenInfo);
		return tokenInfo.getString("access_token");
	}
	
	public static String getJsapiTicket(String ACCESS_TOKEN){
		String jsTicketUrl="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + ACCESS_TOKEN +"&type=jsapi";
		String jsapi_ticketInfo=HttpUtil.getUrl(jsTicketUrl);
		try {
			jsapi_ticketInfo = new String(jsapi_ticketInfo.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 JSONObject ticketInfo=JSONObject.parseObject(jsapi_ticketInfo);
		 
		return ticketInfo.getString("ticket");
	}
	
	public static String getCouponTicket(String ACCESS_TOKEN){
		String couponTicketUrl="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + ACCESS_TOKEN +"&type=wx_card";
		String coupon_ticketInfo=HttpUtil.getUrl(couponTicketUrl);
		try {
			coupon_ticketInfo = new String(coupon_ticketInfo.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 JSONObject ticketInfo=JSONObject.parseObject(coupon_ticketInfo);
		 
		return ticketInfo.getString("ticket");
	}
	
	public static void main(String[] args){
		String accessToken=getAccessToken();
		System.out.println(accessToken);
		System.out.println(getJsapiTicket(accessToken));
		String ticket=getCouponTicket(accessToken);
		System.out.println(ticket);
	}
}
