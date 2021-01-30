package com.nsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class EvaluationTable {
    @TableId
    private Integer evaluationId;
    private Integer studentId;
    private Integer courseId;
    private String workReport;
    private String evaluation;
    private Integer result;
}
