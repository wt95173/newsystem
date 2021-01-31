package com.nsystem.controller;

import com.nsystem.entity.Choice;
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

    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    @ResponseBody
    public int setChoice(Integer courseId,Integer level,HttpSession session){
        return studentService.setChoice(courseId, level, session);
    }

    @RequestMapping(value = "/choice",method = RequestMethod.POST)
    @ResponseBody
    public TableVo getChoice(HttpSession session){
        return studentService.getChoice(session);
    }

    @RequestMapping("/projects")
    public TableVo getProject(Integer page, Integer limit,HttpSession session){
        return studentService.getProjects(page,limit,session);
    }

    @RequestMapping("/participation")
    public int participation(String projectId,HttpSession session){
        return studentService.participation(projectId, session);
    }

    @RequestMapping("/myprojects")
    public TableVo getMyProject(HttpSession session){
        return studentService.getMyProjects(session);
    }

    @RequestMapping("/record")
    public TableVo getRecord(Integer page,Integer limit,String projectId,HttpSession session){
        return studentService.getRecord(page, limit, projectId, session);
    }





}
