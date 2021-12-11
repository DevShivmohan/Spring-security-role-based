package com.spring.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddController {

    @RequestMapping(value = "/portal",method = RequestMethod.GET)
    public String get(){
        return "Welcome in Crash portal";
    }
}
