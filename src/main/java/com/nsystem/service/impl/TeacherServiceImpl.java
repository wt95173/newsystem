package com.nsystem.service.impl;

import com.nsystem.entity.LoginInformation;
import com.nsystem.service.TeacherService;
import com.nsystem.vo.LoginVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Override
    public LoginVo getUserName(HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("teacher");
        LoginVo loginVo=new LoginVo();
        loginVo.setUsername(loginInformation.getUserName());
        return loginVo;
    }
}
