package com.course.ucenter.constants;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("wx.open")
@Data
public class WxOpenConstants implements InitializingBean {

    /**
     * 微信开放平台appId
     */
    private String appId;

    /**
     * 微信开放平台appSecret
     */
    private String appSecret;

    /**
     * 微信开放平台 重定向url
     */
    private String redirectUrl;


    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
