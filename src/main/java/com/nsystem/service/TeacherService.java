package com.nsystem.service;

import com.nsystem.vo.LoginVo;

import javax.servlet.http.HttpSession;

public interface TeacherService {
    public LoginVo getUserName(HttpSession session);
}
