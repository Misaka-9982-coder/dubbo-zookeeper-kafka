package com.misaka.service.impl;

import com.misaka.pojo.User;
import com.misaka.service.UserService;
import org.apache.dubbo.config.annotation.Service;

@Service(weight = 100)
public class UserServiceImpl implements UserService {
    public String sayHello() {
        return "weight 100";
    }

    @Override
    public User getUserById(int id) {
        System.out.println("old Version..");
        User user = new User(id, "misaka" + id, "123456" + id);

        return user;
    }
}
