/**
 * @Title: IpUtil.java
 * @Package com.haistand.web.util
 * @Description:上海海略管理信息咨询有限公司 Haistand Consulting Company 版权所有
 */
package com.utils;

import java.net.*;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HTTP;


public class IpUtil {
    /**
     * @ClassName: IpUtil
     * @Description: ip工具
     * @author 陈超
     * @date 2015年7月13日 下午2:25:59
     *
     */

    /**
     * 将字符串型ip转成int型ip
     * @param strIp
     * @return
     */
    public static int ip2Int(String strIp) {
        String[] ss = strIp.split("\\.");
        if (ss.length != 4) {
            return 0;
        }
        byte[] bytes = new byte[ss.length];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(ss[i]);
        }
        return byte2Int(bytes);
    }

    /**
     * 将int型ip转成String型ip
     * @param intIp
     * @return
     */
    public static String int2Ip(int intIp) {
        byte[] bytes = int2byte(intIp);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(bytes[i] & 0xFF);
            if (i < 3) {
                sb.append(".");
            }
        }
        return sb.toString();
    }

    private static byte[] int2byte(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (0xff & i);
        bytes[1] = (byte) ((0xff00 & i) >> 8);
        bytes[2] = (byte) ((0xff0000 & i) >> 16);
        bytes[3] = (byte) ((0xff000000 & i) >> 24);
        return bytes;
    }

    private static int byte2Int(byte[] bytes) {
        int n = bytes[0] & 0xFF;
        n |= ((bytes[1] << 8) & 0xFF00);
        n |= ((bytes[2] << 16) & 0xFF0000);
        n |= ((bytes[3] << 24) & 0xFF000000);
        return n;
    }


    /**
     * 获取IP地址
     * @param request
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * 获取当前网络ip
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     *
     * @param IP
     * @return
     */
    public static String GetAddressByIp(String IP) {
        String resout = "";
        String str =null;
        try {
            str = HttpUtil.getUrl("http://ip.taobao.com/service/getIpInfo.php?ip=" + IP);
            JSONObject obj = JSONObject.parseObject(str);
            JSONObject obj2 = obj.getJSONObject("data");
            String code = obj.getString("code");
            if (code.equals("0")) {

                resout = obj2.getString("region");
                if(resout.equals("内蒙古自治区")){
                    resout="内蒙古";
                }else{
                    resout=   resout.replace("省","").replace("市","");
                }
            } else {
                resout = "IP地址有误";
            }
        } catch (Exception e) {

            e.printStackTrace();
            resout = "获取IP地址异常：" +str;
        }
        return resout;

    }

    /**
        *
        * @param IP
        * @return
        */
       public static String GetIpAddressInfoByAli( String IP) {
           String resout = "";
           String str =null;
           try {
               String url = "http://ali-ip.showapi.com/ip?ip=" +IP ;

               HttpGet httpGet =new HttpGet(url);
               httpGet.setHeader("Authorization","APPCODE ce11d8ecdf794f10b8ec5e26e6d4ab01");
               str = HttpUtil.getUrl(httpGet,url);
               JSONObject obj = JSONObject.parseObject(str);
               JSONObject obj2 = obj.getJSONObject("showapi_res_body");
               String code = obj.getString("showapi_res_code");
               if (code.equals("0")) {

                   resout = obj2.getString("region");
                   if(resout.equals("内蒙古自治区")){
                       resout="内蒙古";
                   }else{
                       resout=   resout.replace("省","").replace("市","");
                   }
               } else {
                   resout = "IP地址有误";
               }
           } catch (Exception e) {

               e.printStackTrace();
               resout = "获取IP地址异常：" +str;
           }
           return resout;

       }

public static void main(String[] args){
    String province=GetAddressByIp("223.72.64.84");
          System.out.println(province);
}

}
