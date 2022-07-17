package com.misaka.controller;

import com.misaka.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("/sayHello")
    public String sayHello() {
        return userService.sayHello();
    }
}
