package com.web.entity;

public class Configure {

    /**
     * 微信接入token ，用于验证微信接口
     */
    public static String TOKEN = "251ea921";

    /**
     * 商户号
     */
    public static final String mch_id = "";

    /**
     * APPID
     */
    public static final String WXAppid = "";

    /**
     * AppSecret
     */
    public static final String AppSecret = "";


    /**
     * 商户名称
     */
    public static final String SendName = "大学密码";
//	public static final String SendName="anyoemaotong";

    /**
     * 祝福语
     */
    public static final String Wishing = "感谢您参加全球大学创业精神中国调查";
//	public static final String Wishing="thank you";

    /**
     * 调用接口的机器Ip地址
     */
    public static final String ClientIp = "101.81.13.200";

    /**
     * 活动名称
     */
    public static final String act_name = "全球大学创业精神调查";
//	public static final String act_name="happy everyday";

    /**
     * 备注信息
     */
    public static final String remark = "感谢您参加全球大学创业精神中国调查";
//	public static final String remark="share to your partner";

    /**
     * 回调url
     */
    public static final String url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";

    public static final String group_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendgroupredpack";


    /**
     * 红包金额设置方式 ,ALL_RAND—全部随机,商户指定总金额和红包发放总人数，由微信支付随机计算出各红包金额
     */
    public static final String amt_type = "ALL_RAND";


    //	public static final String openid="ouohpwNJpCFhKGWiv9FZXZqT-3oU";
    public static final String openid = "ouohpwCaZcA4T80FD7qD78wXFgDs";


}
