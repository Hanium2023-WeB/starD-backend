package com.web.stard.domain.board.study.dto;

import lombok.Getter;

@Getter
public class Top5Dto {
    private String field;
    private Long count;

    public Top5Dto(String field, Long count) {
        this.field = field;
        this.count = count;
    }
}
