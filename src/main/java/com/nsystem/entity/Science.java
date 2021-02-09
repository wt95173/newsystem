package com.nsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Science {
    @TableId
    private Integer scienceId;
    private String scienceName;
    private String sciencePlace;
    private Date scienceTime;
    private String scienceTitle;
    private String scienceImage;
    private String scienceNote;
    private Integer scienceType;
}
