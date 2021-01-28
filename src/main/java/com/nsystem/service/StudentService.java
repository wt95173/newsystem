package com.nsystem.service;

import com.nsystem.vo.CourseVo;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.TableVo;

import javax.servlet.http.HttpSession;

public interface StudentService {
    public LoginVo getUserName(HttpSession session);

    public TableVo<CourseVo> getCourse(Integer page,Integer limit);
}
