package com.nsystem.service;

import com.nsystem.entity.Choice;
import com.nsystem.entity.Course;
import com.nsystem.entity.Project;
import com.nsystem.entity.Science;
import com.nsystem.vo.CourseVo;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.StudentVo;
import com.nsystem.vo.TableVo;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public interface AdminService {
    public LoginVo getUserName(HttpSession session);

    public TableVo<StudentVo> getStudent(Integer page,Integer limit);

    public int appointStudent(Integer studentId,Integer courseId);

    public TableVo<Project> getProject(Integer page,Integer limit);

    public int editProject(String projectId,String projectType,String projectName);

    public int addProject(String projectId,String projectType, String projectName);

    public List<Course> getCourse(Integer studentId);

    public int editScience(int scienceId, String scienceName, String sciencePlace, Date scienceTime, String scienceTitle, String scienceImage, String scienceNote, Integer scienceType);

    public int addScience(int scienceId, String scienceName, String sciencePlace, Date scienceTime, String scienceTitle, String scienceImage, String scienceNote, Integer scienceType);

    public TableVo<Science> getScience(Integer page, Integer limit);
}
