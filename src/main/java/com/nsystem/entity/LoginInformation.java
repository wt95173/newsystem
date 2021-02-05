package com.nsystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class LoginInformation {
    @TableId
    private Integer relativeId;
    private String password;
    private Integer state;
    private String userName;
    private String lastLoginIp;
    private Date lastLoginTime;
}
