package com.nsystem.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LoginInformation {
    private Integer relativeId;
    private String password;
    private Integer state;
    private String userName;
    private String lastLoginIp;
    private Date lastLoginTime;
}
