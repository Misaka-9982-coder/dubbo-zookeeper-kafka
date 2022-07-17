package com.misaka.service.impl;

import com.misaka.pojo.User;
import com.misaka.service.UserService;
import org.apache.dubbo.config.annotation.Service;

@Service(timeout = 3000, retries = 0)
public class UserServiceImpl implements UserService {
    public String sayHello() {
        return "Hello world!";
    }

    @Override
    public User getUserById(int id) {
        User user = new User(id, "misaka" + id, "123456" + id);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
}
