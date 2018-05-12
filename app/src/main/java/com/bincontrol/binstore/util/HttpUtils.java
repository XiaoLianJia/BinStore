package com.bincontrol.binstore.util;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

    /**
     * Http POST请求
     * @param url url
     * @param jsonObject json object
     * @return json object
     */
    public static JSONObject post(String url, JSONObject jsonObject) {

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8");

            StringEntity stringEntity = new StringEntity(jsonObject.toJSONString());
            stringEntity.setContentType("text/json");
            stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8"));

            httpPost.setEntity(stringEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);

            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpResponse.getEntity());
                return JSONObject.parseObject(result);

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
