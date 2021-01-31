package com.nsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class StudentProject {
    private Integer spid;
    private Float spfund;
    private String spwork;
    private Date sptimeBegin;
    private Date sptimeEnd;
    private Integer studentId;
    private String projectId;
}
