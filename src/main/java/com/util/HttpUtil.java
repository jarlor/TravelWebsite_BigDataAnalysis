package com.util;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {

    private HttpUtil() {

    }

    private static final HttpUtil httpUtil = new HttpUtil();

    public static HttpUtil getInstance() {
        return httpUtil;
    }

    public String httpGet(HashMap<String, String> params, String urlStr) {
        StringBuffer result = new StringBuffer();
        BufferedReader reader = null;
        try {
            URL url = new URL(getUrl(params, urlStr));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(120000);
            connection.setReadTimeout(120000);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    public String httpPost(String urlStr, HashMap<String, String> params) {
        StringBuffer sb = new StringBuffer();
        try {
            //创建连接
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(120000);
            connection.setReadTimeout(120000);
            // head
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.write(getBodyParams(params).getBytes("utf-8")); // 需指定编码格式，否则中文无法发送
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String sendRequest(final String path, final String method, final String postData) throws Exception {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method); //必须大写
        connection.setConnectTimeout(5000);  // 设置连接超时时间
        connection.setReadTimeout(5000);     //设置读取超时时间
        connection.setDoInput(true);
        connection.setDoOutput(true);
        if (postData != null && method.equals("POST") && !postData.isEmpty())      //像服务端提交数据
        {
            //输出数据
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            //将数据写入到输出流中
            out.writeBytes(postData);
        }

        if (connection.getResponseCode() == 200) {
            //获取服务端响应的流
            InputStream is = connection.getInputStream();
            //解析输入流
            return parseIs(is);
        }
        return null;
    }

    private static String parseIs(InputStream is) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        byte[] chars = new byte[2014];
        try {
            while ((len = is.read(chars)) != -1) {
                bos.write(chars, 0, len);
            }
            String str = new String(bos.toByteArray());
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 拼接POST请求参数
     *
     * @param params
     * @return
     */
    private String getBodyParams(HashMap<String, String> params) {
        StringBuffer sb = new StringBuffer();
        // 添加url参数
        if (params != null && params.size() > 0) {
            try {
                Iterator<Entry<String, String>> entryKeyIterator = params.entrySet().iterator();
                while (entryKeyIterator.hasNext()) {
                    Entry<String, String> entry = entryKeyIterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    sb.append(URLEncoder.encode(key, "UTF-8"));
                    sb.append("=");
                    sb.append(URLEncoder.encode(value, "UTF-8"));
                    sb.append("&");
                }
                int position = sb.lastIndexOf("&");
                sb.replace(position, position + 1, "");
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    /**
     * 拼接GET请求参数
     *
     * @param params
     * @param url
     * @return
     */
    private String getUrl(Map<String, String> params, String url) {
        // 添加url参数
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key).toString();
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                try {
                    sb.append(URLEncoder.encode(key, "UTF-8"));
                    sb.append("=");
                    sb.append(URLEncoder.encode(value, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            url += sb.toString();
        }
        return url;
    }
}
