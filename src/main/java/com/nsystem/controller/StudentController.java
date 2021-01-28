package com.nsystem.controller;

import com.nsystem.entity.LoginInformation;
import com.nsystem.entity.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/student")
public class StudentController {
    @RequestMapping(value = "/getUserName",method = RequestMethod.POST)
    @ResponseBody
    public String getUserName(HttpSession session){
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        return loginInformation.getUserName();
    }
}
