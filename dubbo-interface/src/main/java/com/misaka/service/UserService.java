package com.misaka.service;


import com.misaka.pojo.User;

public interface UserService {
    public String sayHello();

    public User getUserById(int id);
}
