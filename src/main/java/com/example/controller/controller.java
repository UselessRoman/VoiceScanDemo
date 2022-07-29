package com.example.controller;

import com.example.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
public class controller {

    @Autowired
    private Service service;

    @RequestMapping(value = "/a",method = RequestMethod.POST)
    public void test(@RequestBody String text){

        try {
            text = URLDecoder.decode(text,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            service.run(text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}