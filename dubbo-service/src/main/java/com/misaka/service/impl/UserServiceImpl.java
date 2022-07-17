package com.misaka.service.impl;

import com.misaka.service.UserService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class UserServiceImpl implements UserService {
    public String sayHello() {
        return "Hello world!";
    }
}
