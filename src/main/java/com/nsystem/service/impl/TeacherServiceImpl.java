package com.nsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsystem.entity.Course;
import com.nsystem.entity.LoginInformation;
import com.nsystem.entity.TeacherCourse;
import com.nsystem.mapper.CourseMapper;
import com.nsystem.mapper.TeacherCourseMapper;
import com.nsystem.service.TeacherService;
import com.nsystem.vo.CourseVo;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.TableVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public LoginVo getUserName(HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("teacher");
        LoginVo loginVo=new LoginVo();
        loginVo.setUsername(loginInformation.getUserName());
        return loginVo;
    }

    @Override
    public TableVo<CourseVo> getCourse(Integer page, Integer limit,HttpSession session) {
        TableVo tableVo=new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        LoginInformation loginInformation=(LoginInformation) session.getAttribute("teacher");
        Integer teacherId=loginInformation.getRelativeId();

        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("teacher_id",teacherId);

        List<TeacherCourse> teacherCourseList=teacherCourseMapper.selectList(wrapper);
        QueryWrapper wrapper1=new QueryWrapper();
        //QueryWrapper wrapper2=new QueryWrapper();

        for(TeacherCourse teacherCourse:teacherCourseList){
            wrapper1.eq("course_id",teacherCourse.getCourseId());
            wrapper1.or();
        }

        tableVo.setCount(courseMapper.selectCount(wrapper1));

        IPage<Course> courseIPage=new Page<>(page,limit);
        IPage<Course> result=courseMapper.selectPage(courseIPage,wrapper1);
        List<Course> courseList=result.getRecords();
        List<CourseVo> courseVoList=new ArrayList<>();

        for(Course course:courseList){
            CourseVo courseVo=new CourseVo();
            BeanUtils.copyProperties(course,courseVo);
            courseVoList.add(courseVo);
        }

        tableVo.setData(courseVoList);

        return tableVo;
    }
}
