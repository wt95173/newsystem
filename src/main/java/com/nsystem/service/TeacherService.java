package com.nsystem.service;

import com.nsystem.vo.CourseVo;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.StudentVo;
import com.nsystem.vo.TableVo;

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
}
