package com.nsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Science {
    private Integer scienceId;
    private String scienceName;
    private String sciencePlace;
    private Date scienceTime;
    private String scienceTitle;
    private String scienceImage;
    private String scienceNote;
    private Integer scienceType;
}
