package com.nsystem.controller;

import com.nsystem.entity.Course;
import com.nsystem.entity.LoginInformation;
import com.nsystem.entity.Project;
import com.nsystem.service.AdminService;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.TableVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/getUserName",method = RequestMethod.POST)
    @ResponseBody
    public LoginVo getUserName(HttpSession session){
        return adminService.getUserName(session);
    }
    @RequestMapping("/stanbylist")
    public TableVo getStudent(Integer page,Integer limit){
        return adminService.getStudent(page,limit);
    }

    @RequestMapping("/appoint")
    public int appointStudent(Integer studentId,Integer courseId) {
        return adminService.appointStudent(studentId, courseId);
    }

    @RequestMapping("/projects")
    public TableVo getProject(Integer page,Integer limit){
        return adminService.getProject(page, limit);
    }

    @RequestMapping("editproject")
    public int editProject(String projectId,String projectType,String projectName){
        return adminService.editProject(projectId, projectType, projectName);
    }

    @RequestMapping("addproject")
    public int addProject(String projectId,String projectType, String projectName){
        return adminService.addProject(projectId, projectType, projectName);
    }

    @RequestMapping("option")
    public List<Course> getCourse(Integer studentId){
        return adminService.getCourse(studentId);
    }

}
