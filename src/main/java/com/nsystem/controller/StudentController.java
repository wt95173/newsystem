package com.nsystem.controller;

import com.nsystem.service.StudentService;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/getUserName",method = RequestMethod.POST)
    @ResponseBody
    public LoginVo getUserName(HttpSession session){
        return studentService.getUserName(session);
    }

    @RequestMapping("/course")
    public TableVo getCourseList(Integer page, Integer limit){
        return studentService.getCourse(page, limit);
    }
}
