package com.sphong.aop_example;

import com.sphong.aop_example.domain.Board;
import com.sphong.aop_example.domain.BoardRepository;
import com.sphong.aop_example.domain.User;
import com.sphong.aop_example.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AopExampleApplication implements CommandLineRunner {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public AopExampleApplication(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(AopExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for(int i=1;i<=100;i++){
            boardRepository.save(new Board(i+"번째 게시글의 제목", i+"번째 게시글의 내용"));
            userRepository.save(new User(i+"@email.com", i+"번째 사용자"));
        }
    }
}
