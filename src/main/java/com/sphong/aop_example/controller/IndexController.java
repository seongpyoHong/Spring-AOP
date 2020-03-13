package com.sphong.aop_example.controller;

import com.sphong.aop_example.domain.Board;
import com.sphong.aop_example.domain.User;
import com.sphong.aop_example.service.BoardService;
import com.sphong.aop_example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {
    private final UserService userService;
    private final BoardService boardService;

    public IndexController(UserService userService, BoardService boardService) {
        this.userService = userService;
        this.boardService = boardService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/boards")
    public List<Board> getBoards() {
        return boardService.getBoards();
    }
}
