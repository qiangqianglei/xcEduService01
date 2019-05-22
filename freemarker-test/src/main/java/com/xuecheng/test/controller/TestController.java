package com.xuecheng.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/freemarker")
public class TestController {

    @RequestMapping("/test1")
    public String test1(Map<String,Object> map){

        map.put("name","Lqq nice!");

        return "test1";
    }
}
