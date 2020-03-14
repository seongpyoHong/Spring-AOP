package com.sphong.aop_example.service;

import com.sphong.aop_example.domain.User;
import com.sphong.aop_example.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user) throws Exception {
        userRepository.save(user);
    }
}
