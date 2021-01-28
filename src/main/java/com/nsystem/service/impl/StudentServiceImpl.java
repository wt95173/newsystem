package com.nsystem.service.impl;

import com.nsystem.entity.LoginInformation;
import com.nsystem.service.StudentService;
import com.nsystem.vo.LoginVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class StudentServiceImpl implements StudentService {

    @Override
    public LoginVo getUserName(HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        LoginVo loginVo=new LoginVo();
        loginVo.setUsername(loginInformation.getUserName());
        return loginVo;
    }
}
