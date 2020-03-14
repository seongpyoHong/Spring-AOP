package com.sphong.aop_example.aspect;

import com.sphong.aop_example.domain.History;
import com.sphong.aop_example.domain.HistoryRepository;
import com.sphong.aop_example.domain.User;
import com.sphong.aop_example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UpdateHistoryTest {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserService userService;

    @Test
    public void updateUsers() throws Exception {
        List<User> userList = userService.getUsers();
        for (int i = 0; i<5; i++) {
            User user = userList.get(i);
            user.setEmail("sphong@email.com");
            userService.update(user);
        }

        List<History> histories = historyRepository.findAll();
        assertEquals(histories.size(), 5);
        assertEquals(histories.get(0).getUserId(), 1);

    }
}