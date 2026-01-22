package com.dd.admin.common.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络访问工具类
 */
public class PureNetUtils {



    private static final Logger log = LoggerFactory.getLogger(PureNetUtils.class);

    /**
     * get方法直接调用post方法
     *
     * @param url 网络地址
     * @return 返回网络数据
     */
    public static String get(String url) {
        return post(url, null);
    }

    /**
     * 设定post方法获取网络资源,如果参数为null,实际上设定为get方法
     *
     * @param url   网络地址
     * @param param 请求参数键值对
     * @return 返回读取数据
     */
    public static String post(String url, Map<String, String> param) {
        HttpURLConnection conn = null;
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            StringBuilder sb = null;
            if (param != null) {// 如果请求参数不为空
                sb = new StringBuilder();
                /*
                 * A URL connection can be used for input and/or output. Set the
                 * DoOutput flag to true if you intend to use the URL connection
                 * for output, false if not. The default is false.
                 */
                // 默认为false,post方法需要写入参数,设定true
                conn.setDoOutput(true);
                // 设定post方法,默认get
                conn.setRequestMethod("POST");
                // 获得输出流
                OutputStream out = conn.getOutputStream();
                // 对输出流封装成高级输出流
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                // 将参数封装成键值对的形式
                for (Map.Entry<String, String> s : param.entrySet()) {
                    sb.append(s.getKey()).append("=").append(s.getValue()).append("&");
                }
                // 将参数通过输出流写入
                writer.write(sb.deleteCharAt(sb.toString().length() - 1).toString());
                writer.close();// 一定要关闭,不然可能出现参数不全的错误
                sb = null;
            }
            conn.connect();// 建立连接
            sb = new StringBuilder();
            // 获取连接状态码
            int recode = conn.getResponseCode();
            BufferedReader reader = null;
            if (recode == 200) {
                // Returns an input stream that reads from this open connection
                // 从连接中获取输入流
                InputStream in = conn.getInputStream();
                // 对输入流进行封装
                reader = new BufferedReader(new InputStreamReader(in));
                String str = null;
                sb = new StringBuilder();
                // 从输入流中读取数据
                while ((str = reader.readLine()) != null) {
                    sb.append(str).append(System.getProperty("line.separator"));
                }
                // 关闭输入流
                reader.close();
                if (sb.toString().length() == 0) {
                    return null;
                }
                return sb.toString().substring(0,
                        sb.toString().length() - System.getProperty("line.separator").length());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (conn != null)// 关闭连接
                conn.disconnect();
        }
        return null;
    }

    /**
     * 获取 ip 所属地址
     *
     * @param ip IP 地址
     * @return 所属地址
     */
    public static String getAlibaba(String ip) {
        Map<String, String> map = new HashMap<>();
        map.put("ip", ip);
        map.put("accessKey", "alibaba-inc");
        String result = PureNetUtils.post("http://ip.taobao.com/outGetIpInfo", map);
        log.info("{} => POST: http://ip.taobao.com/outGetIpInfo || result: {}", ip, result);
        String address = null;
        if (StringUtils.isNotBlank(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            // 请求成功，解析响应数据
            if ("query success".equals(jsonObject.get("msg"))) {
                JSONObject dataMap = JSONObject.parseObject(jsonObject.getString("data"));
                String country = dataMap.getString("country");
                String region = dataMap.getString("region");
                String city = dataMap.getString("city");
                address = country + region + city;
            }
        }
        return address;
    }

}

