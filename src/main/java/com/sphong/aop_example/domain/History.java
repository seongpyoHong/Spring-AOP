package com.sphong.aop_example.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class History {
    @Id
    @GeneratedValue
    private Long idx;

    @Column
    private Long userId;

    @Column
    private Date updateDate;

    @Builder
    public History(Long userId, Date updateDate) {
        this.userId = userId;
        this.updateDate = updateDate;
    }
}
