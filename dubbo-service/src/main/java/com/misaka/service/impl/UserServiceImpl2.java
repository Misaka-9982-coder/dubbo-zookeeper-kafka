package com.misaka.service.impl;

import com.misaka.pojo.User;
import com.misaka.service.UserService;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "2.0.0")
public class UserServiceImpl2 implements UserService {
    public String sayHello() {
        return "Hello world!";
    }

    @Override
    public User getUserById(int id) {
        System.out.println("new Version..");
        User user = new User(id, "misaka" + id, "123456" + id);

        return user;
    }
}
