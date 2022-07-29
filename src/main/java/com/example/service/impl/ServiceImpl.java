package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.service.Service;
import com.example.utils.HttpUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    private static final String WEBTTS_URL = "http://ltpapi.xfyun.cn/v1/ke";
    // 应用ID
    private static final String APPID = "6df4d356";
    // 接口密钥
    private static final String API_KEY = "98f9523cdd186559cb12bd77f468b56e";
    // 文本
    private static String TEXT;
    private static final String TYPE = "dependent";

    @Override
    public void run(String text) throws UnsupportedEncodingException {

        TEXT = text;
        System.out.println("----------------------------------------------------------------------------------");
        Map<String, String> header = buildHttpHeader();
        String result = HttpUtil.doPost1(WEBTTS_URL, header, "text=" + URLEncoder.encode(TEXT, "utf-8"));

        JSONObject jsonobject = JSONObject.parseObject(result);

        JSONObject data = (JSONObject) jsonobject.get("data");

        List<JSONObject> value = (List<JSONObject>) data.get("ke");

        for (JSONObject object:
                value) {
            String score=object.getString("score");
            String word=object.getString("word");
            try
            {
                if(Double.parseDouble(score)>0.55)
                {
                    System.out.println("’"+word+"‘的权重："+score);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    /**
     * 组装http请求头
     */
    private static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
        String curTime = System.currentTimeMillis() / 1000L + "";
        String param = "{\"type\":\"" + TYPE + "\"}";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
        String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-CheckSum", checkSum);
        header.put("X-Appid", APPID);
        return header;
    }

    class keyWord
    {
        private String word;
        private double score;

        public String getWord() {
            return word;
        }

        @Override
        public String toString() {
            return "keyWord{" +
                    "word='" + word + '\'' +
                    ", score=" + score +
                    '}';
        }

        public void setWord(String word) {
            this.word = word;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
