package com.peanut.fs.common.util;


import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * @description:
 * @author:zfs
 * @date:created in 10:22 2019/7/22
 */
@Slf4j
public class GMapUtil {

    private static final String KEY = "e8f2282b9a86da1969eef373a87e77bb";

    private static final String PREFIX_KEY = "key=";

    private static final String PREFIX_ADDRESS = "&address=";

    private static final double X_PI = 3.14159265358979324 * 3000.0 / 180.0;


    /**
     * 请求参数类型
     * 100代表道路
     * 010代表POI
     * 001代表门址
     * 111可以同时显示前三项
     */
    private static final String REQUEST_TYPE = "&type=010";

    private static final String GET_LAT_AND_LON_BY_NAME_URL = "http://restapi.amap.com/v3/geocode/geo";



    /**
     * 高德api根据地址获取经纬度
     *
     * @param name
     * @return
     */
    public static String getLatitudeAndLongitudeByName(String name) {
        log.info("[GMapUtil.getLatitudeAndLongitudeByName]根据名称获取经纬度开始name:{}", name);
        String requestString = PREFIX_KEY + KEY + PREFIX_ADDRESS + name;
        String res = sendPost(GET_LAT_AND_LON_BY_NAME_URL, requestString);
        log.info(res);
        JSONObject jsonObject = JSONObject.fromObject(res);
        JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("geocodes"));
        JSONObject location = (JSONObject) jsonArray.get(0);
        log.info("[GMapUtil.getLatitudeAndLongitudeByName]根据名称获取经纬度结束location:{}", location);
        return location.get("location").toString();
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
     *
     * @param latitude
     * @param longitude
     */
    public static double[] gcj02ToBd09(double longitude, double latitude) {
        double transDouble = Math.sqrt(longitude * longitude + latitude * latitude) + 0.00002 * Math.sin(latitude * X_PI);
        double theta = Math.atan2(latitude, longitude) + 0.000003 * Math.cos(longitude * X_PI);
        double tempLongitude = transDouble * Math.cos(theta) + 0.0065;
        double tempLatitude = transDouble * Math.sin(theta) + 0.006;
        return new double[]{tempLongitude, tempLatitude};
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            log.info("[GMapUtil.sendPost]发送请求开始");
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("[GMapUtil.sendPost]发送POST请求出现异常e:{}", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("[GMapUtil.sendPost]关闭流异常ex:{}", ex);
            }
        }
        return result.toString();
    }

    /**
     * 返回起始地startAddr与目的地endAddr之间的距离，单位：米
     * @param startLonLat 起始经纬度
     * @param endLonLat 结束经纬度
     * @return 距离
     */
    public static Long getDistance(String startLonLat, String endLonLat){
        log.info("[GMapUtil.getDistance]获取两个经纬度之间距离开始startLonLat:{}, endLonLat:{}", startLonLat, endLonLat);
        String requestParam = PREFIX_KEY + KEY + "&origins=" + startLonLat + "&destination=" + endLonLat;
        Long disResult = Long.MIN_VALUE;
        String queryUrl = "http://restapi.amap.com/v3/distance";
        String distanceResult = sendPost(queryUrl, requestParam);
        JSONObject jsonObject = JSONObject.fromObject(distanceResult);
        JSONArray results = jsonObject.getJSONArray("results");
        Object distance = JSONObject.fromObject(results.getString(0)).get("distance");
        if(distance != null){
            disResult = Long.parseLong(distance.toString());
        }
        log.info("[GMapUtil.getDistance]获取两个经纬度之间距离结束disResult:{}", disResult);
        return disResult;
    }




    
}
