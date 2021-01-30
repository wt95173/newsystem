package com.nsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsystem.entity.*;
import com.nsystem.mapper.*;
import com.nsystem.service.TeacherService;
import com.nsystem.vo.CourseVo;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.StudentVo;
import com.nsystem.vo.TableVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherCourseMapper teacherCourseMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ChoiceMapper choiceMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private EvaluationTableMapper evaluationTableMapper;

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

    @Override
    public TableVo<StudentVo> getStudent(Integer page, Integer limit, Integer courseId) {
        TableVo tableVo = new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");
        int x=0;

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id", courseId);

        List<Choice> choiceList = choiceMapper.selectList(wrapper);

        QueryWrapper wrapper1 = new QueryWrapper();
        for (Choice choice : choiceList) {
            if (choice.getState()==0) {
                x++;
                wrapper1.eq("student_id", choice.getStudentId());
                wrapper1.or();
            }
        }

        if (x==0) {
            tableVo.setCount(0);
            tableVo.setData(null);

        } else {
            tableVo.setCount(studentMapper.selectCount(wrapper1));
            IPage<Student> studentIPage = new Page<>(page, limit);
            IPage<Student> result = studentMapper.selectPage(studentIPage, wrapper1);
            List<Student> studentList = result.getRecords();
            List<StudentVo> studentVoList = new ArrayList<>();

            for (Student student : studentList) {
                StudentVo studentVo = new StudentVo();
                BeanUtils.copyProperties(student, studentVo);
                QueryWrapper wrapper2 = new QueryWrapper();
                wrapper2.eq("major_id", student.getMajorId());
                studentVo.setMajorName(majorMapper.selectOne(wrapper2).getMajorName());
                if (student.getSex().equals(0)) {
                    studentVo.setSex("男");
                } else {
                    studentVo.setSex("女");
                }
                studentVoList.add(studentVo);
            }
            tableVo.setData(studentVoList);
        }
        x=0;
        return tableVo;
    }

    @Override
    public TableVo<StudentVo> getChoiceStudent(Integer page, Integer limit, Integer courseId) {
        TableVo tableVo = new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");
        List<StudentVo> studentVoList=new ArrayList<>();


        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id", courseId);
        if(evaluationTableMapper.selectOne(wrapper)!=null){
            EvaluationTable evaluationTable=evaluationTableMapper.selectOne(wrapper);

            Integer studentId=evaluationTable.getStudentId();

            QueryWrapper wrapper1 = new QueryWrapper();
            wrapper1.eq("student_id", studentId);

            Student student=studentMapper.selectOne(wrapper1);

            StudentVo studentVo = new StudentVo();
            BeanUtils.copyProperties(student, studentVo);
            QueryWrapper wrapper2 = new QueryWrapper();
            wrapper2.eq("major_id", student.getMajorId());
            studentVo.setMajorName(majorMapper.selectOne(wrapper2).getMajorName());
            if (student.getSex().equals(0)) {
                studentVo.setSex("男");
            } else {
                studentVo.setSex("女");
            }
            studentVoList.add(studentVo);
            tableVo.setData(studentVoList);
            tableVo.setCount(1);
        }else{
            tableVo.setCount(0);
            tableVo.setData(null);

        }
        return tableVo;

    }

    @Override
    public int passStudent(Integer studentId, Integer courseId) {
        EvaluationTable evaluationTable=new EvaluationTable();
        evaluationTable.setStudentId(studentId);
        evaluationTable.setCourseId(courseId);
        List<EvaluationTable> evaluationTableList=evaluationTableMapper.selectList(null);
        evaluationTable.setEvaluationId(evaluationTableList.get(evaluationTableList.size()-1).getEvaluationId()+1);

        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("course_id",courseId);
        wrapper.ne("student_id",studentId);

        List<Choice> choiceList=choiceMapper.selectList(wrapper);
        for(Choice choice:choiceList){
            choice.setState(2);
            choiceMapper.updateById(choice);
        }

        QueryWrapper wrapper1=new QueryWrapper();
        wrapper1.eq("course_id",courseId);
        wrapper1.eq("student_id",studentId);

        Choice choice1=choiceMapper.selectOne(wrapper1);
        choice1.setState(1);
        choiceMapper.updateById(choice1);
        return evaluationTableMapper.insert(evaluationTable);
    }


}
