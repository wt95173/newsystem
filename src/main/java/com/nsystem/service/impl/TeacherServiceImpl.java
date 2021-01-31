package com.nsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsystem.entity.*;
import com.nsystem.mapper.*;
import com.nsystem.service.TeacherService;
import com.nsystem.vo.*;
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
    @Autowired
    private TeacherProjectMapper teacherProjectMapper;
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private StudentProjectMapper studentProjectMapper;
    @Autowired
    private ProjectRecordMapper projectRecordMapper;

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

    @Override
    public TableVo<StudentVo> getTeacherStudent(HttpSession session) {
        TableVo tableVo = new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        LoginInformation loginInformation=(LoginInformation) session.getAttribute("teacher");
        Integer teacherId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("teacher_id",teacherId);
        List<TeacherCourse> teacherCourseList=teacherCourseMapper.selectList(wrapper);
        List<StudentVo> studentVoList=new ArrayList<>();

        for(TeacherCourse teacherCourse:teacherCourseList){
            Integer courseId=teacherCourse.getCourseId();
            QueryWrapper wrapper1=new QueryWrapper();
            wrapper1.eq("course_id",courseId);

            List<EvaluationTable> evaluationTableList=evaluationTableMapper.selectList(wrapper1);
            for(EvaluationTable evaluationTable:evaluationTableList){
                Integer studentId=evaluationTable.getStudentId();
                QueryWrapper wrapper2=new QueryWrapper();
                wrapper2.eq("student_id",studentId);
                StudentVo studentVo=new StudentVo();
                Student student=studentMapper.selectOne(wrapper2);
                BeanUtils.copyProperties(student,studentVo);
                QueryWrapper wrapper3 = new QueryWrapper();
                wrapper3.eq("major_id", student.getMajorId());
                studentVo.setMajorName(majorMapper.selectOne(wrapper3).getMajorName());
                if (student.getSex().equals(0)) {
                    studentVo.setSex("男");
                } else {
                    studentVo.setSex("女");
                }
                if(evaluationTable.getResult()!=null){
                    if(evaluationTable.getResult()==1){
                        studentVo.setResult(1);
                    }else{
                        studentVo.setResult(0);
                    }
                }else{
                    studentVo.setResult(null);
                }
                studentVoList.add(studentVo);
            }
        }
        tableVo.setCount(studentVoList.size());
        tableVo.setData(studentVoList);
        return tableVo;
    }

    @Override
    public int passTeacherStudent(Integer studentId, Integer result) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);
        EvaluationTable evaluationTable=evaluationTableMapper.selectOne(wrapper);
        if(result==1){
            evaluationTable.setResult(1);
        }else{
            evaluationTable.setResult(2);
        }
        return evaluationTableMapper.updateById(evaluationTable);
    }

    @Override
    public TableVo<Project> getMyProject(HttpSession session) {
        TableVo tableVo = new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        LoginInformation loginInformation=(LoginInformation) session.getAttribute("teacher");
        Integer teacherId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("teacher_id",teacherId);

        QueryWrapper wrapper1=new QueryWrapper();
        if(teacherProjectMapper.selectList(wrapper).size()!=0){
            List<TeacherProject> teacherProjectList=teacherProjectMapper.selectList(wrapper);
            for(TeacherProject teacherProject:teacherProjectList){
                wrapper1.eq("project_id",teacherProject.getProjectId());
                wrapper1.or();
            }
            List<Project> projectList=projectMapper.selectList(wrapper1);

            tableVo.setCount(projectList.size());
            tableVo.setData(projectList);
        }else{

        }
        return tableVo;
    }

    @Override
    public TableVo<Project> getProject(Integer page, Integer limit, HttpSession session) {
        TableVo tableVo = new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        LoginInformation loginInformation=(LoginInformation) session.getAttribute("teacher");
        Integer teacherId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("teacher_id",teacherId);

        QueryWrapper wrapper1=new QueryWrapper();

        List<TeacherProject> teacherProjectList=teacherProjectMapper.selectList(wrapper);
        for(TeacherProject teacherProject:teacherProjectList){
            wrapper1.ne("project_id",teacherProject.getProjectId());
        }
        tableVo.setCount(projectMapper.selectCount(wrapper1));

        IPage<Project> projectIPage=new Page<>(page,limit);
        IPage<Project> result=projectMapper.selectPage(projectIPage,wrapper1);
        List<Project> projectList=result.getRecords();

        tableVo.setData(projectList);

        return tableVo;
    }

    @Override
    public int addProject(String projectId, HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("teacher");
        Integer teacherId=loginInformation.getRelativeId();
        TeacherProject teacherProject=new TeacherProject();
        teacherProject.setTeacherId(teacherId);
        teacherProject.setTpfund((float)20);
        teacherProject.setProjectId(projectId);
        List<TeacherProject> teacherProjectList=teacherProjectMapper.selectList(null);
        teacherProject.setTpid(teacherProjectList.get(teacherProjectList.size()-1).getTpid()+1);
        return teacherProjectMapper.insert(teacherProject);
    }

    @Override
    public TableVo<RecordVo> getRecord(Integer page, Integer limit, String projectId) {
        TableVo tableVo = new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        int x=0;

        List<RecordVo> recordVoList=new ArrayList<>();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("project_id",projectId);

        QueryWrapper wrapper1=new QueryWrapper();
        List<StudentProject> studentProjectList=studentProjectMapper.selectList(wrapper);
        for(StudentProject studentProject:studentProjectList){
            QueryWrapper wrapper2=new QueryWrapper();
            wrapper2.eq("spid",studentProject.getSpid());
            List<ProjectRecord> projectRecordList=projectRecordMapper.selectList(wrapper2);
            for(ProjectRecord projectRecord:projectRecordList){
                wrapper1.eq("prid",projectRecord.getPrid());
                wrapper1.or();
                x++;
            }
        }
        if(x==0){

        }else{
            tableVo.setCount(projectRecordMapper.selectCount(wrapper1));

            IPage<ProjectRecord> projectRecordIPage=new Page<>(page,limit);
            IPage<ProjectRecord> result=projectRecordMapper.selectPage(projectRecordIPage,wrapper1);
            List<ProjectRecord> projectRecordList=result.getRecords();

            for(ProjectRecord projectRecord1:projectRecordList){
                RecordVo recordVo=new RecordVo();
                BeanUtils.copyProperties(projectRecord1,recordVo);
                QueryWrapper wrapper3=new QueryWrapper();
                wrapper3.eq("spid",projectRecord1.getSpid());
                Integer studentId=studentProjectMapper.selectOne(wrapper3).getStudentId();
                QueryWrapper wrapper4=new QueryWrapper();
                wrapper4.eq("student_id",studentId);
                String studentName=studentMapper.selectOne(wrapper4).getStudentName();
                recordVo.setStudentName(studentName);
                recordVoList.add(recordVo);
            }
            tableVo.setData(recordVoList);
        }

        x=0;
        return tableVo;
    }

    @Override
    public TableVo<StudentVo> getStudent2(String projectId) {
        TableVo tableVo = new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");
        int x=0;

        List<StudentVo> studentVoList=new ArrayList<>();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("project_id",projectId);
        List<StudentProject> studentProjectList=studentProjectMapper.selectList(wrapper);

        QueryWrapper wrapper1=new QueryWrapper();

        for(StudentProject studentProject:studentProjectList){
            wrapper1.eq("student_id",studentProject.getStudentId());
            wrapper1.or();
            x++;
        }
        if(x==0){

        }else{
            List<Student> studentList=studentMapper.selectList(wrapper1);
            for(Student student:studentList){
                StudentVo studentVo=new StudentVo();
                BeanUtils.copyProperties(student,studentVo);
                QueryWrapper wrapper3 = new QueryWrapper();
                wrapper3.eq("major_id", student.getMajorId());
                studentVo.setMajorName(majorMapper.selectOne(wrapper3).getMajorName());
                if (student.getSex().equals(0)) {
                    studentVo.setSex("男");
                } else {
                    studentVo.setSex("女");
                }
                studentVoList.add(studentVo);
            }
            tableVo.setCount(studentVoList.size());
            tableVo.setData(studentVoList);
        }
        x=0;
        return tableVo;
    }

}
