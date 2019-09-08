package com.peanut.fs.common.util.wechat;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WechatUtil {

    private  static final String APP_ID = "wx8a6441d728926e02";

    private static final String APP_SECRET = "1edf236c6189bdbe060039092f209c29";


    public static String getSessionKey(String code){
        log.info("[WechatUtil.getSessionKey]获取sessionKey开始code:{}", code);
        String tokenUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        tokenUrl = tokenUrl.replace("APPID",APP_ID).replace("SECRET",APP_SECRET).replace("JSCODE", code);
        HttpUtil httpUtil = new HttpUtil();
        String resultToken = httpUtil.doGet(tokenUrl).getBody();
        JSONObject jsonObject = JSONUtil.parseObj(resultToken);
        log.info("[WechatUtil.getSessionKey]获取sessionKey结束jsonObject:{}", jsonObject);
        return jsonObject.getStr("session_key");
    }

    public static String getPhone(String encryptData, String sessionKey, String iv){
        log.info("[WechatUtil.getPhone]获取phoneNo开始sessionKey:{}", sessionKey);
        String phoneNumber = null;
        JSONObject userEncryptedData = WechatSecreatUtil.wxDecrypt(encryptData, sessionKey, iv);
        log.info("[WechatUtil.getPhone]获取phoneNo结束userEncryptedData:{}", userEncryptedData);
        if (StringUtils.isNotBlank((String) userEncryptedData.get("phoneNumber"))){
            phoneNumber = (String) userEncryptedData.get("phoneNumber");
        }
        log.info("[WechatUtil.getPhone]获取phoneNo结束phoneNo:{}", phoneNumber);
        return phoneNumber;
    }


//    public static void main(String[] args) {
//        System.out.println(getPhone("wLa8RZWfg7qAUs4sKBoldSjKEeIluGFr5hXLnOwjNUr/wdgPXnoPjj7gxeoF/T0ylSCdop4QE8gFrUkDHw6VdsuRw2XbxDNmiz1Mq+EymF4PAbFINbwrHptU1q94sNQMhJXgNP/wAyPGfU7CP8+4AYFyUkYghmXuYehIw/5Maiur2jL4pj43Fb9Quk4Nvf7o9pupF9hGPxCLN6gyCWxnjg==",
//                getSessionKey("0018b56b0wpray1Jjq8b0c9l6b08b560"), "6bKIm6Oybhd+/fgJa9cYWQ=="));
//    }



}

