package com.sphong.aop_example.aspect;

import com.sphong.aop_example.domain.History;
import com.sphong.aop_example.domain.HistoryRepository;
import com.sphong.aop_example.domain.User;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;

@Component
@Aspect
public class UpdateHistory {
    @Autowired
    private HistoryRepository historyRepository;

    @Pointcut("execution (* com.sphong.aop_example.service.UserService.update(*)) && args(user)")
    public void updateUser(User user) {}

    @AfterReturning("updateUser(user)")
    public void saveHistory(User user) {
        Date updateDate = new Date();
        historyRepository.save(History.builder().userId(user.getIdx()).updateDate(updateDate).build());
    }
}
