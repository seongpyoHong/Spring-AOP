package com.sphong.aop_example.service;

import com.sphong.aop_example.domain.Board;
import com.sphong.aop_example.domain.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<Board> getBoards() {
        return boardRepository.findAll();
    }
}
