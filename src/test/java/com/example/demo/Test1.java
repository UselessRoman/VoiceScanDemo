package com.example.demo;


import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Test1 {

    @Test
    public void test()
    {
        String result = "{\"code\":\"0\",\"data\":{\"ke\":[{\"score\":\"0.619\",\"word\":\"江安\"},{\"score\":\"0.616\",\"word\":\"校区\"},{\"score\":\"0.607\",\"word\":\"二基楼\"},{\"score\":\"0.586\",\"word\":\"四川\"},{\"score\":\"0.585\",\"word\":\"大学\"},{\"score\":\"0.547\",\"word\":\"你好\"},{\"score\":\"0.512\",\"word\":\"这里\"},{\"score\":\"0.504\",\"word\":\"这个\"},{\"score\":\"0.500\",\"word\":\"22\"}]},\"desc\":\"success\",\"sid\":\"ltp000e9c43@dx36941631bc781aba00\"}";


        JSONObject jsonobject = JSONObject.parseObject(result);

        JSONObject data = (JSONObject) jsonobject.get("data");

        List<JSONObject> value = (List<JSONObject>) data.get("ke");

        for (JSONObject object:
             value) {
            String score=object.getString("score");
            String word=object.getString("word");

               System.out.println("’"+word+"‘的权重："+score);

        }
    }
}
