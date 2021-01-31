package com.nsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Project {
    @TableId
    private String projectId;
    private String projectType;
    private String projectName;
}
