package com.nsystem.vo;

import lombok.Data;

@Data
public class CourseVo {
    private Integer courseId;
    private String courseName;
    private Integer time;
    private Integer type;
    private Integer teachObject;
    private Integer studentNum;
    private Integer term;
}
