package com.nsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Choice {
    @TableId
    private Integer choiceId;

    private Integer courseId;

    private Integer studentId;

    private Integer level;

    private Integer state;
}