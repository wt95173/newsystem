package com.nsystem.service;

import com.nsystem.entity.Choice;
import com.nsystem.entity.Project;
import com.nsystem.entity.ProjectRecord;
import com.nsystem.entity.Science;
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

    public TableVo<Project> getMyProjects(HttpSession session);

    public TableVo<ProjectRecord> getRecord(Integer page,Integer limit,String projectId,HttpSession session);

    public int setRecord(String projectId,String recordTitle,String recordInfo,String recordResolve,HttpSession session);

    public TableVo<Science> getScience(Integer page, Integer limit, HttpSession session);

    public int attend(int  scienceId, HttpSession session);

    public TableVo<Science> getMySciences(HttpSession session);
}
