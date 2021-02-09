package com.nsystem.service;

import com.nsystem.entity.Project;
import com.nsystem.entity.Science;
import com.nsystem.vo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface TeacherService {
    public LoginVo getUserName(HttpSession session);

    public TableVo<CourseVo> getCourse(Integer page, Integer limit,HttpSession session);

    public TableVo<StudentVo> getStudent(Integer page, Integer limit,  Integer courseId);

    public TableVo<StudentVo> getChoiceStudent(Integer page, Integer limit,  Integer courseId);

    public int passStudent(Integer studentId,Integer courseId);

    public TableVo<StudentVo> getTeacherStudent(HttpSession session);

    public int passTeacherStudent(Integer studentId,Integer result);

    public TableVo<Project> getMyProject(HttpSession session);

    public TableVo<Project> getProject(Integer page, Integer limit,HttpSession session);

    public int addProject(String projectId,HttpSession session);

    public TableVo<RecordVo> getRecord(Integer page, Integer limit,String projectId);

    public TableVo<StudentVo> getStudent2(String projectId);

    public TableVo<Science> getMyScience(HttpSession session);

    public TableVo<Science> getScience(Integer page, Integer limit, HttpSession session);

     public int addScience(int scienceId, HttpSession session);

    public TableVo<StudentVo> getSStudent(int scienceId);
}
