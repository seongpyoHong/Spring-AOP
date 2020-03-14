package com.sphong.aop_example.controller;

import com.sphong.aop_example.domain.Board;
import com.sphong.aop_example.domain.User;
import com.sphong.aop_example.service.BoardService;
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
class IndexControllerTest {
    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Test
    public void getBoardsTest() {
        //when
        List<Board> boardList = boardService.getBoards();
        //then
        assertEquals(boardList.size(), 100);
    }

    @Test
    public void getUsersTest() {
        //when
        List<User> userList = userService.getUsers();
        //then
        assertEquals(userList.size(), 100);
    }
}