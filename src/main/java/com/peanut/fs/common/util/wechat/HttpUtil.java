package com.peanut.fs.common.util.wechat;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class HttpUtil {

    /**
     * DoGet方法
     * @param url 请求地址
     * @return 请求结果
     */
    public ResponseEntity<String> doGet(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(url, String.class);
    }

    /**
     * DoGet方法
     * @param url 请求地址
     * @param responseType 返回类型
     * @return 请求结果
     */
    public ResponseEntity<T> doGet(String url, Class<T> responseType) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(url, responseType);
    }

    /**
     * DoPost方法
     * @param url 请求地址
     * @param httpHeaders 请求头
     * @param uriVariables 请求参数
     * @param responseType 返回类型
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ResponseEntity<T> doPost(String url, HttpHeaders httpHeaders, Map<String, ?> uriVariables, Class<T> responseType) {
        /* 请求头 */
        MultiValueMap headers = null;
        if(httpHeaders == null) {
            headers = new HttpHeaders();
        }else {
            headers = httpHeaders;
        }
        /* 请求内容封装成HttpEntity */
        HttpEntity httpEntity= new HttpEntity(uriVariables, headers);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(url, httpEntity, responseType);
    }

    /**
     * DoPost方法
     * @param url 请求地址
     * @param params 请求参数
     * @param responseType 返回类型
     * @return
     */
    public ResponseEntity<T> doPost(String url, String params, Class<T> responseType) {
        /* 请求内容封装成HttpEntity */
        HttpEntity<String> httpEntity= new HttpEntity<String>(params);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(url, httpEntity, responseType);
    }

}
