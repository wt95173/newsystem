package com.nsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsystem.entity.Course;
import com.nsystem.entity.EvaluationTable;
import com.nsystem.entity.LoginInformation;
import com.nsystem.mapper.CourseMapper;
import com.nsystem.mapper.EvaluationTableMapper;
import com.nsystem.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private EvaluationTableMapper evaluationTableMapper;

    @Override
    public LoginVo getUserName(HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        LoginVo loginVo=new LoginVo();
        loginVo.setUsername(loginInformation.getUserName());
        return loginVo;
    }

    @Override
    public TableVo<CourseVo> getCourse(Integer page,Integer limit) {
        TableVo tableVo=new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");


        QueryWrapper wrapper1=new QueryWrapper();
        List<EvaluationTable> evaluationTableList=evaluationTableMapper.selectList(null);
        for(EvaluationTable evaluationTable:evaluationTableList){
            wrapper1.ne("course_id",evaluationTable.getCourseId());
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