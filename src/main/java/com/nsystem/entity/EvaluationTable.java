package com.nsystem.entity;

import lombok.Data;

@Data
public class EvaluationTable {
    private Integer evaluationId;
    private Integer studentId;
    private Integer courseId;
    private String workReport;
    private String evaluation;
    private Integer result;
}
