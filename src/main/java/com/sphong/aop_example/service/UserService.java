package com.sphong.aop_example.service;

import com.sphong.aop_example.domain.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    void update(User user) throws Exception;
}
