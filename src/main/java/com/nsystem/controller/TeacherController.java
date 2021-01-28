package com.nsystem.controller;

import com.nsystem.service.TeacherService;
import com.nsystem.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/getUserName",method = RequestMethod.POST)
    @ResponseBody
    public LoginVo getUserName(HttpSession session){
       return teacherService.getUserName(session);
    }
}
