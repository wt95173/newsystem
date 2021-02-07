package com.nsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsystem.entity.*;
import com.nsystem.mapper.*;
import com.nsystem.service.AdminService;
import com.nsystem.vo.CourseVo;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.StudentVo;
import com.nsystem.vo.TableVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private EvaluationTableMapper evaluationTableMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public LoginVo getUserName(HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("admin");
        LoginVo loginVo=new LoginVo();
        loginVo.setUsername(loginInformation.getUserName());
        return loginVo;
    }

    @Override
    public TableVo<StudentVo> getStudent(Integer page, Integer limit) {
        TableVo tableVo=new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");
        List<StudentVo> studentVoList=new ArrayList<>();

        QueryWrapper wrapper=new QueryWrapper();
        List<EvaluationTable> evaluationTableList=evaluationTableMapper.selectList(null);
        for(EvaluationTable evaluationTable:evaluationTableList){
            wrapper.ne("student_id",evaluationTable.getStudentId());
        }
        tableVo.setCount(studentMapper.selectCount(wrapper));
        IPage<Student> studentIPage=new Page<>(page,limit);
        IPage<Student> result=studentMapper.selectPage(studentIPage,wrapper);
        List<Student> studentList=result.getRecords();

        for(Student student:studentList){
            StudentVo studentVo=new StudentVo();
            BeanUtils.copyProperties(student,studentVo);
            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.eq("major_id", student.getMajorId());
            studentVo.setMajorName(majorMapper.selectOne(wrapper1).getMajorName());
            if (student.getSex().equals(0)) {
                studentVo.setSex("男");
            } else {
                studentVo.setSex("女");
            }
            studentVoList.add(studentVo);
        }
        tableVo.setData(studentVoList);
        return tableVo;
    }

    public int appointStudent(Integer studentId,Integer courseId){
        EvaluationTable evaluationTable=new EvaluationTable();
        evaluationTable.setStudentId(studentId);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("course_id",courseId);
        if(evaluationTableMapper.selectOne(wrapper)!=null){
            return -1;
        }else{
            evaluationTable.setCourseId(courseId);
            List<EvaluationTable> evaluationTableList=evaluationTableMapper.selectList(null);
            evaluationTable.setEvaluationId(evaluationTableList.get(evaluationTableList.size()-1).getEvaluationId()+1);
            return evaluationTableMapper.insert(evaluationTable);
        }
    }

    @Override
    public TableVo<Project> getProject(Integer page, Integer limit) {
        TableVo tableVo=new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        IPage<Project> projectIPage=new Page<>(page,limit);
        IPage<Project> result=projectMapper.selectPage(projectIPage,null);
        List<Project> projectList=result.getRecords();
        tableVo.setCount(projectMapper.selectCount(null));
        tableVo.setData(projectList);

        return tableVo;
    }

    @Override
    public int editProject(String projectId, String projectType, String projectName) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("project_id",projectId);
        Project project=projectMapper.selectOne(wrapper);
        project.setProjectType(projectType);
        project.setProjectName(projectName);
        return projectMapper.updateById(project);
    }

    @Override
    public int addProject(String projectId,String projectType, String projectName) {
        Project project=new Project();
        project.setProjectName(projectName);
        project.setProjectType(projectType);
        project.setProjectId(projectId);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("project_id",projectId);
        if(projectMapper.selectOne(wrapper)!=null){
            return -1;
        }else{
            return projectMapper.insert(project);
        }
    }

    @Override
    public TableVo<CourseVo> getCourse(Integer studentId) {
        TableVo tableVo=new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);
        Integer majorId=studentMapper.selectOne(wrapper).getMajorId();
        QueryWrapper wrapper1=new QueryWrapper();
        wrapper1.eq("major_id",majorId);
        List<Course> courseList=courseMapper.selectList(wrapper1);
        tableVo.setData(courseList);
        tableVo.setCount(courseList.size());
        return tableVo;
    }
}
