package com.nsystem.service;

import com.nsystem.entity.Choice;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.StudentVo;
import com.nsystem.vo.TableVo;

import javax.servlet.http.HttpSession;

public interface AdminService {
    public LoginVo getUserName(HttpSession session);

    public TableVo<StudentVo> getStudent(Integer page,Integer limit);

    public int appointStudent(Integer studentId,Integer courseId);
}
