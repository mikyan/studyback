package com.heartsuit.showcase.modules.web.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController//如果是前后端分离的项目，可以直接使用这个RestController代替Controller
@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/api/aboutschool")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

}