package com.misaka.controller;

import com.misaka.pojo.User;
import com.misaka.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired // 本地注入
    @Reference  // 远程注入
    private UserService userService;


    @RequestMapping("/sayHello")
    public String sayHello() {
        return userService.sayHello();
    }

    @RequestMapping("/get")
    public User get(int id) {
        return userService.getUserById(id);
    }
}
