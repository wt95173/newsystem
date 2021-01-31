package com.nsystem.controller;

import com.nsystem.service.TeacherService;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.RecordVo;
import com.nsystem.vo.StudentVo;
import com.nsystem.vo.TableVo;
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

    @RequestMapping("/course")
    public TableVo getCourseList(Integer page, Integer limit, HttpSession session){
        return teacherService.getCourse(page,limit,session);
    }

    @RequestMapping("/applylist")
    public TableVo getStudent(Integer page, Integer limit,  Integer courseId){
        return teacherService.getStudent(page, limit, courseId);
    }

    @RequestMapping("/courseassistant")
    public TableVo getChoiceStudent(Integer page, Integer limit,  Integer courseId){
        return teacherService.getChoiceStudent(page, limit, courseId);
    }

    @RequestMapping("/pass")
    public int passStudent(Integer studentId, Integer courseId){
        return teacherService.passStudent(studentId, courseId);
    }

    @RequestMapping("/teacherassistants")
    public TableVo getTeacherStudent(HttpSession session){
        return teacherService.getTeacherStudent(session);
    }

    @RequestMapping("/evaluate")
    public int passTeacherStudent(Integer studentId, Integer result){
        return teacherService.passTeacherStudent(studentId, result);
    }

    @RequestMapping("/myprojects")
    public TableVo getMyProject(HttpSession session){
        return teacherService.getMyProject(session);
    }

    @RequestMapping("/projects")
    public TableVo getProject(Integer page, Integer limit, HttpSession session){
        return teacherService.getProject(page, limit, session);
    }

    @RequestMapping("/addproject")
    public int addProject(String projectId,HttpSession session){
        return teacherService.addProject(projectId, session);
    }

    @RequestMapping("/record")
    public TableVo getRecord(Integer page, Integer limit, String projectId){
        return teacherService.getRecord(page, limit, projectId);
    }

    @RequestMapping("/prostu")
    public TableVo getStudent2(String projectId){
        return teacherService.getStudent2(projectId);
    }
}
