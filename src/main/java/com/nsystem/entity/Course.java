package com.nsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Course {
    private Integer courseId;
    private String serialNum;
    private String courseName;
    private Integer time;
    private Integer type;
    private Integer teachObject;
    private Integer studentNum;
    private Date openingYear;
    private Integer term;
    private Integer priority;
}
