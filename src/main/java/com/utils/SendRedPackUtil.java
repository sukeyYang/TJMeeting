package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.web.entity.Configure;

public class SendRedPackUtil {

    private static CloseableHttpClient buildClient() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File("/home/cert/apiclient_cert.p12"));
        try {
            keyStore.load(instream, Configure.mch_id.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
            .loadKeyMaterial(keyStore, Configure.mch_id.toCharArray())
            .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
            sslcontext,
            new String[] {"TLSv1"},
            null,
            SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
            .setSSLSocketFactory(sslsf)
            .build();
        return httpclient;
    }

    private static String genXml(Map<String, String> map, String sign) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
//		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
        sb.append("<xml>");
        sb.append("<sign>");
        sb.append("<![CDATA[").append(sign).append("]]>");
        sb.append("</sign>");

        for (String key : map.keySet()) {
            sb.append("<").append(key).append(">");
            sb.append("<![CDATA[").append(map.get(key)).append("]]>");
            sb.append("</").append(key).append(">");
        }

        sb.append("</xml>");

        return sb.toString();
    }

    public static int getRandom(int min, int max) {

        Random random = new Random();

        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    private static String sentRequestByPost(CloseableHttpClient httpclient, String xml, String url) throws IOException {
        String content = "";
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("User_Agent", "Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) Gecko/20090803");
        httppost.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httppost.setHeader("Accept-Encoding", "gzip, deflate");

        try {
            httppost.setEntity(new StringEntity(xml, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
            httppost.abort();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            httppost.abort();
            e.printStackTrace();
        } finally {
            httpclient.close();
        }

        return content;

    }

    public static void sendRedPack(String openid, String money) throws Exception {
        String nonce_str = getRandomString(32);
        String mch_id = Configure.mch_id;
        String mch_billno = getMchBillno(openid, Configure.mch_id);
        String wxappid = Configure.WXAppid;
        String send_name = Configure.SendName;
        String re_openid = openid;
        String total_amount = money;
        String total_num = "1";
        String wishing = Configure.Wishing;
        String client_ip = Configure.ClientIp;
        String act_name = Configure.act_name;
        String remark = Configure.remark;

        Map<String, String> map = new TreeMap<String, String>();
        map.put("nonce_str", nonce_str);
        map.put("mch_billno", mch_billno);
        map.put("wxappid", wxappid);
        map.put("send_name", send_name);
        map.put("re_openid", re_openid);
        map.put("total_amount", total_amount);
        map.put("total_num", total_num);
        map.put("wishing", wishing);
        map.put("client_ip", client_ip);
        map.put("act_name", act_name);
        map.put("remark", remark);
        map.put("mch_id", mch_id);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        StringBuffer sb = new StringBuffer();
        for (String key : map.keySet()) {
            sb.append(key).append("=").append(map.get(key)).append("&");
            params.add(new BasicNameValuePair(key, map.get(key)));
        }
        String stringSignTemp = sb.append("key=").append(Configure.AppSecret).toString();
        String sign = MD5Util.encryptMD5(stringSignTemp);

        String xml = genXml(map, sign);
        CloseableHttpClient httpclient = buildClient();
        String sentResult = sentRequestByPost(httpclient, xml, Configure.url);
        System.out.println(sentResult);

    }

    public static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }

    private static String getMchBillno(String openid, String mch_id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();

        Random random = new Random();
        String randomStr = FPGenerator.std24.fp(openid + random.nextInt()) + "";
        String mch_billno = mch_id + sdf.format(date) + randomStr.substring(2, 12);
        return mch_billno;
    }

    public static void main(String[] args) {
//		System.out.println(getMchBbillno("sukey"));
        List<String> openids = new ArrayList<String>();
        openids.add("ouohpwFFWu9F_lDEq3AXH9ycIBrI");
        openids.add("ouohpwKYy4NFZaxVf1pW6aopCdMk");
        openids.add("ouohpwMLHNY-G7G-j1FfnPR85bk4");
        openids.add("ouohpwF920nCg44UDWf65BEwTyJc");
        openids.add("ouohpwCfHo8vXzzdsujXd1oYnGFQ");
        openids.add("ouohpwEr9Cbl8GSqz1-k7ldn5BDk");
        openids.add("ouohpwIc1qnnkFnN3gq6l4ByGv7A");
        openids.add("ouohpwByfki2fU3T-9r8HWJn8usg");
        openids.add("ouohpwIlqcjLSFJj06ajmky3QlS8");
        openids.add("ouohpwFIcsIdh4S3HXG0L3sqguM0");
        openids.add("ouohpwHftox2Rsx6jt0sgKu1DyOI");
        openids.add("ouohpwK05WijcmMpS82AbQwYmhGA");
        openids.add("ouohpwJtonUzzisIQPB2cfTlVUmk");
        openids.add("ouohpwNwhZ7-INhmxNXjatJEU2LA");
        openids.add("ouohpwJyvJxXEgGifLShcfPhtbW0");

        try {
            for (String openid : openids) {
                sendRedPack(openid, "118");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
