package com.github.zjjfly.readinglist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author zjjfly
 * @date 2017/7/2
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping
    public String defaultPage(){
        return "login";
    }
    @RequestMapping("/main")
    public String main(){
        return "main";
    }
}
