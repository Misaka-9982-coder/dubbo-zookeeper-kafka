package com.misaka.service.impl;

import com.misaka.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    public String sayHello() {
        return "Hello world!";
    }
}
