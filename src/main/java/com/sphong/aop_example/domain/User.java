package com.sphong.aop_example.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    private Long idx;

    @Column
    private String email;

    @Column
    private String name;

    @Builder
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
