package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

    public static String getUrl(String url) {
        String result = null;
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet(url);

            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);

            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static String getUrl(HttpGet httpGet, String url) {
        String result = null;
        try {
            // 根据地址获取请求
            HttpGet request;
            if (httpGet == null) {
                request = new HttpGet(url);
            } else {
                request = httpGet;
            }


            // 获取当前客户端对象
            HttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);

            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     */
    public static String httpRequest(String requestUrl, String requestMethod,
                                     String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
                .openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            // InputStreamReader inputStreamReader = new
            // InputStreamReader(inputStream, "utf-8");
            InputStreamReader inputStreamReader = new InputStreamReader(
                inputStream);
            BufferedReader bufferedReader = new BufferedReader(
                inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @throws ParseException
     * @throws IOException
     */
    public static void postFile(String url, String paramName, String filepath)
        throws ParseException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            // 要上传的文件的路径
            String filePath = new String(filepath);
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            // 把文件转换成流对象FileBody
            File file = new File(filePath);
            FileBody bin = new FileBody(file);
            /*
             * StringBody uploadFileName = new StringBody("my.png",
			 * ContentType.create("text/plain", Consts.UTF_8));
			 */
            // 以浏览器兼容模式运行，防止文件名乱码。
            HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(
                HttpMultipartMode.BROWSER_COMPATIBLE)
                .addPart("buffer", bin) // uploadFile对应服务端类的同名属性<File类型>
                // .addPart("uploadFileName", uploadFileName)//
                // uploadFileName对应服务端类的同名属性<String类型>
                .setCharset(CharsetUtils.get("UTF-8")).build();

            httpPost.setEntity(reqEntity);

            System.out.println("发起请求的页面地址 " + httpPost.getRequestLine());
            // 发起请求 并返回请求的响应
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                // 打印响应状态
                System.out.println(response.getStatusLine());
                // 获取响应对象
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    // 打印响应长度
                    System.out.println("Response content length: "
                        + resEntity.getContentLength());
                    // 打印响应内容
                    System.out.println(EntityUtils.toString(resEntity, Charset
                        .forName("UTF-8")));
                }
                // 销毁
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

    public static String httpPostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
        String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);

        DefaultHttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");

        StringEntity se = new StringEntity(encoderJson);
        se.setContentType("text/json");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
            "application/json"));
        httpPost.setEntity(se);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        return content;
    }


    public static void main(String[] args) {
        // String
        // requestUrl="https://api.weixin.qq.com/card/testwhitelist/set?access_token=uEn3ohdqp7F8M5gd1hLMP4WdCoUJ5B6NIA9cni4RiNn2PgpFxEsPzSp5Gdt9T0m4eQ9pYZ6GxNjk3yuu0Wajof8x-5vD_gEbT6pEFZA5dMUNAAgADAJWW";
        // String
        // data="{\"openid\": [\"ouohpwNJpCFhKGWiv9FZXZqT-3oU\",\"ouohpwCaZcA4T80FD7qD78wXFgDs\"]}";
        // String result=httpRequest(requestUrl, "POST", data);
        // System.out.println(result);
//        JSONObject data = new JSONObject();
//        data.put("code", "test");
//        data.put("openid", "aa1234");
//        data.put("verify", "true");
//        String requestUrl = "http://www.shchengdian.com/sgmwechat/api/verify_result";
////		String result = httpRequest(requestUrl, "POST", data.toString());
//        String result = null;
//        try {
//            result = httpPostWithJSON(requestUrl, data.toString());
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        List<String> list = new ArrayList<>();

        list.add("140.205.225.185");
        list.add("223.104.3.147");
        list.add("210.13.87.148");
        list.add("1.88.193.233");
        list.add("218.249.221.2");
        list.add("101.228.50.131");
        list.add("121.33.205.236");
        list.add("218.246.71.113");
        list.add("117.136.41.36");
        list.add("106.121.68.98");
        list.add("182.18.6.174");
        list.add("117.136.79.22");
        list.add("117.136.41.88");
        list.add("110.96.32.167");
        list.add("117.136.0.185");
        list.add("124.229.43.26");
        list.add("223.72.100.122");
        list.add("124.200.144.164");
        list.add("117.136.38.59");
        list.add("223.73.201.176");
        list.add("111.192.84.84");
        list.add("183.42.47.237");
        list.add("1.202.190.83");
        list.add("203.192.13.178");
        list.add("117.136.79.49");
        list.add("106.37.15.6");
        list.add("202.104.47.133");
        list.add("117.136.41.67");
        list.add("117.136.0.119");
        list.add("106.120.30.114");
        list.add("223.104.17.69");
        list.add("115.33.59.188");
        list.add("223.72.64.84");
        list.add("117.136.32.93");
        list.add("1.180.212.214");
        list.add("111.197.130.169");
        list.add("119.131.39.19");
        list.add("117.136.41.65");
        list.add("210.73.61.240");
        list.add("211.100.51.149");
        list.add("116.6.30.174");
        list.add("1.180.215.81");
        list.add("114.246.126.194");
        list.add("117.136.0.254");
        list.add("101.242.54.12");
        list.add("114.253.87.208");
        list.add("114.243.44.148");
        list.add("223.73.55.241");
        list.add("124.127.54.155");
        list.add("113.109.42.144");
        list.add("124.65.69.118");
        list.add("42.199.59.181");
        list.add("117.78.234.177");
        list.add("223.104.38.100");
        list.add("101.228.50.131");
        list.add("112.96.164.236");
        list.add("223.104.3.235");
        list.add("111.198.192.190");
        list.add("14.124.241.73");
        list.add("111.199.175.241");
        list.add("106.120.233.59");
        list.add("101.228.50.131");
        list.add("115.183.34.144");
        list.add("61.148.242.188");
        list.add("117.136.0.126");
        list.add("119.32.199.135");
        list.add("59.42.144.10");
        list.add("123.117.29.130");
        list.add("61.140.128.198");
        list.add("113.111.182.46");
        list.add("115.171.199.80");
        list.add("114.250.233.207");
        list.add("61.149.210.5");
        list.add("171.82.165.51");
        list.add("106.121.6.65");
        list.add("61.148.244.252");
        list.add("110.96.32.162");
        list.add("114.253.46.64");
        list.add("117.136.32.109");
        list.add("117.136.0.162");
        list.add("223.104.3.187");
        list.add("106.121.12.189");
        list.add("123.119.36.168");
        list.add("111.196.246.138");
        list.add("119.130.171.163");
        list.add("61.148.242.171");
        list.add("123.65.102.50");
        list.add("112.96.115.181");
        list.add("61.148.242.172");
        list.add("219.137.198.67");
        list.add("223.104.38.97");
        list.add("117.136.38.37");
        list.add("183.60.175.68");
        list.add("222.129.192.139");
        list.add("125.31.217.101");
        list.add("114.242.250.85");
        list.add("14.147.5.71");
        list.add("123.121.160.234");
        list.add("119.131.222.21");
        list.add("121.8.182.66");
        list.add("14.223.176.178");
        list.add("123.116.24.226");
        list.add("39.64.26.55");
        list.add("119.35.230.113");
        list.add("1.180.215.84");
        list.add("114.246.215.88");
        list.add("112.96.33.15");
        list.add("220.198.208.90");
        list.add("61.148.243.157");
        list.add("117.136.41.68");
        list.add("183.6.159.33");
        list.add("111.201.81.76");
        list.add("117.136.41.54");
        list.add("117.136.41.50");
        list.add("114.250.235.16");
        list.add("106.121.11.48");
        list.add("117.136.38.184");
        list.add("61.140.228.113");
        list.add("27.46.236.91");
        list.add("112.17.241.166");
        list.add("123.120.190.149");
        list.add("223.104.38.112");
        list.add("111.199.151.99");
        list.add("117.136.41.39");
        list.add("219.232.196.246");
        list.add("116.199.76.190");
        list.add("223.104.1.103");
        list.add("14.18.29.100");
        list.add("121.32.34.223");
        list.add("123.112.69.181");
        list.add("211.147.253.123");
        list.add("117.136.38.5");
        list.add("202.104.46.151");
        list.add("106.121.9.42");
        list.add("221.218.63.137");
        list.add("223.104.1.255");
        list.add("106.121.12.49");
        list.add("111.194.163.4");
        list.add("119.136.90.56");
        list.add("219.143.155.156");
        list.add("61.148.242.93");
        list.add("220.115.174.82");
        list.add("117.136.0.142");
        list.add("117.136.40.160");
        list.add("119.32.51.112");
        list.add("120.221.74.187");
        list.add("123.114.176.137");
        list.add("111.198.195.196");
        list.add("117.136.31.216");
        list.add("119.131.53.186");
        list.add("223.104.38.65");
        list.add("119.35.6.41");
        list.add("117.136.32.96");
        list.add("119.32.180.83");
        list.add("183.42.40.12");
        list.add("117.136.38.221");
        list.add("117.136.40.135");
        list.add("223.104.3.147");
        list.add("110.96.32.160");
        list.add("58.62.92.20");
        list.add("221.4.42.161");
        list.add("121.32.145.180");
        list.add("223.73.119.196");
        list.add("221.220.66.55");
        list.add("183.3.144.130");
        list.add("123.116.92.41");
        list.add("221.192.180.152");
        list.add("119.80.42.20");
        list.add("117.136.38.144");
        list.add("223.104.1.111");
        list.add("183.238.83.20");
        list.add("106.38.129.88");
        list.add("117.136.41.54");
        list.add("111.192.4.65");
        list.add("114.242.248.239");
        list.add("114.242.250.205");
        list.add("61.148.243.234");
        list.add("117.136.0.193");
        list.add("114.252.32.82");
        list.add("114.253.154.147");
        list.add("125.33.10.123");
        list.add("42.197.99.158");
        list.add("113.45.224.182");
        list.add("113.45.80.53");
        list.add("117.136.40.148");
        list.add("223.104.38.23");
        list.add("114.242.249.212");
        list.add("1.180.212.137");
        list.add("1.180.206.200");
        list.add("14.145.176.9");
        list.add("114.247.79.130");
        list.add("117.136.38.70");
        list.add("119.131.222.108");
        list.add("110.96.32.165");
        list.add("119.130.239.35");
        list.add("222.129.124.222");
        list.add("119.34.122.5");
        list.add("118.247.140.199");
        list.add("223.104.3.199");
        list.add("124.202.231.62");
        list.add("111.198.210.194");
        list.add("112.96.173.117");
        list.add("223.104.3.199");
        list.add("106.121.11.28");
        list.add("223.72.80.159");
        list.add("117.136.38.254");
        list.add("119.34.47.176");
        list.add("223.104.3.179");
        list.add("117.136.38.136");
        list.add("117.136.79.187");
        list.add("183.240.195.238");
        list.add("202.108.180.194");
        list.add("112.96.128.51");
        list.add("117.136.32.83");
        list.add("114.250.131.132");
        list.add("123.120.130.56");
        list.add("223.73.115.94");
        list.add("117.136.31.220");
        list.add("183.42.52.220");
        list.add("221.220.50.133");
        list.add("101.228.50.131");
        list.add("221.216.167.194");
        list.add("106.121.69.240");
        list.add("223.72.66.249");
        list.add("117.136.79.37");
        list.add("117.136.2.172");
        list.add("14.16.54.200");
        list.add("114.241.218.155");
        list.add("112.96.100.159");
        list.add("223.104.1.101");
        list.add("1.180.206.200");
        list.add("61.148.97.118");
        list.add("117.136.0.177");
        list.add("221.217.170.143");
        list.add("117.136.79.12");
        list.add("183.240.20.40");
        list.add("223.73.56.186");
        list.add("120.85.77.18");
        list.add("113.108.186.130");
        list.add("123.164.146.227");
        list.add("114.248.85.181");
        list.add("140.205.225.185");
        list.add("120.221.74.191");
        list.add("183.48.22.17");
        list.add("115.231.149.140");
        list.add("113.108.198.218");
        list.add("119.35.242.146");
        list.add("14.113.70.145");
        list.add("59.42.6.166");
        list.add("223.104.7.54");
        list.add("58.62.122.133");
        list.add("140.205.225.185");
        for (String ip : list) {

            String province = IpUtil.GetIpAddressInfoByAli(ip);
            System.out.println(ip + "-" + province);

        }

//        String ip ="222.129.124.222";
//        String province = IpUtil.GetAddressByIp(ip);
//        System.out.println(ip + "-" + province);

    }
}
