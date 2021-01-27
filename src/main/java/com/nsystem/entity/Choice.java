package com.nsystem.entity;

import lombok.Data;

@Data
public class Choice {
    private Integer choiceId;
    private Integer courseId;
    private Integer studentId;
    private Integer level;
}
