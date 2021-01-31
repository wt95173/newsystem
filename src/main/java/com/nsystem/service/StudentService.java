package com.nsystem.service;

import com.nsystem.entity.Choice;
import com.nsystem.entity.Project;
import com.nsystem.vo.CourseVo;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.TableVo;

import javax.servlet.http.HttpSession;

public interface StudentService {
    public LoginVo getUserName(HttpSession session);

    public TableVo<CourseVo> getCourse(Integer page,Integer limit);

    public int setChoice(Integer courseId,Integer level,HttpSession session);

    public TableVo<Choice> getChoice(HttpSession session);

    public TableVo<Project> getProjects(Integer page,Integer limit,HttpSession session);

    public int participation(String projectId,HttpSession session);

}
