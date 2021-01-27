package com.nsystem.entity;

import lombok.Data;

@Data
public class TeacherProject {
    private Integer tpId;
    private String projectId;
    private Integer teacherId;
    private Float tpfund;
}
