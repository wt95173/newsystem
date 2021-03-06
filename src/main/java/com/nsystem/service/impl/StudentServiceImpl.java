package com.nsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsystem.entity.*;
import com.nsystem.mapper.*;
import com.nsystem.service.StudentService;
import com.nsystem.vo.CourseVo;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.TableVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.DelegatingServletInputStream;
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

    @Autowired
    private ChoiceMapper choiceMapper;

    @Autowired
    private StudentProjectMapper studentProjectMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectRecordMapper projectRecordMapper;

    @Autowired
    private StujoinMapper stujoinMapper;

    @Autowired
    private ScienceMapper scienceMapper;

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

    public int insert(Integer courseId, Integer level,Integer studentId){
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);
        //wrapper.eq("course_Id",courseId);
        wrapper.eq("level",level);
        Choice choice=new Choice();
        if(choiceMapper.selectOne(wrapper)!=null){
            if(choiceMapper.selectOne(wrapper).getCourseId().equals(courseId)){
                return 1;
            }else{
                choice.setCourseId(courseId);
                choice.setStudentId(studentId);
                choice.setLevel(level);
                choice.setState(0);
                List<Choice> choiceList=choiceMapper.selectList(null);
                choice.setChoiceId(choiceList.get(choiceList.size()-1).getChoiceId()+1);
                QueryWrapper wrapper1=new QueryWrapper();
                wrapper1.eq("student_id",studentId);
                wrapper1.eq("course_id",courseId);
                if(choiceMapper.selectOne(wrapper1)!=null){
                    choiceMapper.delete(wrapper1);
                }
                choiceMapper.delete(wrapper);
                return choiceMapper.insert(choice);
            }
        }
        else {
            choice.setCourseId(courseId);
            choice.setStudentId(studentId);
            choice.setLevel(level);
            choice.setState(0);
            List<Choice> choiceList=choiceMapper.selectList(null);
            choice.setChoiceId(choiceList.get(choiceList.size()-1).getChoiceId()+1);
            QueryWrapper wrapper2 = new QueryWrapper();
            wrapper2.eq("student_id", studentId);
            wrapper2.eq("course_id", courseId);
            if (choiceMapper.selectOne(wrapper2) != null) {
                choiceMapper.delete(wrapper2);
            }
            choiceMapper.delete(wrapper);
            return choiceMapper.insert(choice);
        }
    }


    @Override
    public int setChoice(Integer courseId, Integer level, HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        Integer studentId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);
        //wrapper.eq("course_Id",courseId);
        wrapper.eq("level",level);
        Choice choice=new Choice();

        QueryWrapper wrapper3=new QueryWrapper();
        wrapper3.eq("student_id",studentId);
        wrapper3.eq("level",1);
        QueryWrapper wrapper4=new QueryWrapper();
        wrapper4.eq("student_id",studentId);
        wrapper4.eq("level",2);
        if(choiceMapper.selectOne(wrapper3)!=null&&choiceMapper.selectOne(wrapper4)==null){
            if(choiceMapper.selectOne(wrapper3).getState().equals(1)||choiceMapper.selectOne(wrapper3).getState().equals(2)){
                if(level==1){
                    return -1;
                }else{
                    return insert(courseId,level,studentId);
                }
            }else{
                return insert(courseId,level,studentId);
            }
        }else if(choiceMapper.selectOne(wrapper3)==null&&choiceMapper.selectOne(wrapper4)!=null){
            if(choiceMapper.selectOne(wrapper4).getState().equals(1)||choiceMapper.selectOne(wrapper4).getState().equals(2)){
                if(level==2){
                    return -1;
                }else{
                    return insert(courseId,level,studentId);
                }
            }else{
                return insert(courseId,level,studentId);
            }
        }else if(choiceMapper.selectOne(wrapper3)!=null&&choiceMapper.selectOne(wrapper4)!=null){
            if((choiceMapper.selectOne(wrapper3).getState().equals(1)||choiceMapper.selectOne(wrapper3).getState().equals(2))&&choiceMapper.selectOne(wrapper4).getState().equals(1)||choiceMapper.selectOne(wrapper4).getState().equals(2)){
                return -1;
            }else if((choiceMapper.selectOne(wrapper3).getState().equals(1)||choiceMapper.selectOne(wrapper3).getState().equals(2))&&choiceMapper.selectOne(wrapper4).getState().equals(0)){
                if(level==1){
                    return -1;
                }else{
                    return insert(courseId,level,studentId);
                }
            }else if((choiceMapper.selectOne(wrapper4).getState().equals(1)||choiceMapper.selectOne(wrapper4).getState().equals(2))&&choiceMapper.selectOne(wrapper3).getState().equals(0)){
                if(level==2){
                    return -1;
                }else {
                    return insert(courseId,level,studentId);
                }
            }else{
                return insert(courseId,level,studentId);
            }
        }else{
            return insert(courseId,level,studentId);
        }

    }

    @Override
    public TableVo<Choice> getChoice(HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        Integer studentId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);
        List<Choice> choiceList=choiceMapper.selectList(wrapper);
        TableVo tableVo=new TableVo();
        tableVo.setData(choiceList);
        tableVo.setCode(0);
        tableVo.setMsg("");
        tableVo.setCount(choiceList.size());
        return tableVo;
    }

    @Override
    public TableVo<Project> getProjects(Integer page,Integer limit, HttpSession session) {
        TableVo tableVo=new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        Integer studentId=loginInformation.getRelativeId();

        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);

        QueryWrapper wrapper1=new QueryWrapper();
        if(studentProjectMapper.selectList(wrapper)!=null){
            List<StudentProject>  studentProjectList=studentProjectMapper.selectList(wrapper);
            for(StudentProject studentProject:studentProjectList){
                wrapper1.ne("project_id",studentProject.getProjectId());
            }
            IPage<Project> projectIPage=new Page<>(page,limit);
            IPage<Project> result=projectMapper.selectPage(projectIPage,wrapper1);
            List<Project> projectList=result.getRecords();

            tableVo.setCount(projectMapper.selectCount(wrapper1));
            tableVo.setData(projectList);
        }else{

        }
        return tableVo;
    }

    @Override
    public int participation(String projectId, HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        Integer studentId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);
        StudentProject studentProject=new StudentProject();
        studentProject.setProjectId(projectId);
        studentProject.setStudentId(studentId);
        List<StudentProject> studentProjectList=studentProjectMapper.selectList(null);
        studentProject.setSpid(studentProjectList.get(studentProjectList.size()-1).getSpid()+1);
        return studentProjectMapper.insert(studentProject);
    }

    @Override
    public TableVo<Project> getMyProjects(HttpSession session) {
        TableVo tableVo=new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        Integer studentId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);

        QueryWrapper wrapper1=new QueryWrapper();
        if(studentProjectMapper.selectList(wrapper).size()!=0){
            List<StudentProject> studentProjectList=studentProjectMapper.selectList(wrapper);
            for(StudentProject studentProject:studentProjectList){
                wrapper1.eq("project_id",studentProject.getProjectId());
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
    public TableVo<ProjectRecord> getRecord(Integer page,Integer limit,String projectId, HttpSession session) {
        TableVo tableVo=new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        Integer studentId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("project_id",projectId);
        wrapper.eq("student_id",studentId);
        Integer spid=studentProjectMapper.selectOne(wrapper).getSpid();
        QueryWrapper wrapper1=new QueryWrapper();
        wrapper1.eq("spid",spid);
        IPage<ProjectRecord> projectRecordIPage=new Page<>(page,limit);
        IPage<ProjectRecord> result=projectRecordMapper.selectPage(projectRecordIPage,wrapper1);
        List<ProjectRecord> projectRecordList=result.getRecords();
        tableVo.setCount(projectRecordMapper.selectCount(wrapper1));
        tableVo.setData(projectRecordList);
        return tableVo;
    }

    @Override
    public int setRecord(String projectId, String recordTitle, String recordInfo, String recordResolve,HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        Integer studentId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("project_id",projectId);
        wrapper.eq("student_id",studentId);
        Integer spid=studentProjectMapper.selectOne(wrapper).getSpid();

        ProjectRecord projectRecord=new ProjectRecord();
        projectRecord.setRecordInfo(recordInfo);
        projectRecord.setRecordResolve(recordResolve);
        projectRecord.setRecordTitle(recordTitle);

        List<ProjectRecord> projectRecordList=projectRecordMapper.selectList(null);
        projectRecord.setSpid(spid);
        projectRecord.setPrid(projectRecordList.get(projectRecordList.size()-1).getPrid()+1);

        return projectRecordMapper.insert(projectRecord);
    }
    @Override
    public TableVo<Science> getScience(Integer page, Integer limit, HttpSession session) {
        TableVo tableVo=new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        Integer studentId=loginInformation.getRelativeId();

        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);

        QueryWrapper wrapper1=new QueryWrapper();
        if(stujoinMapper.selectList(wrapper)!=null){
            List<Stujoin>  stujoinList=stujoinMapper.selectList(wrapper);
            for(Stujoin stujoin:stujoinList){
                wrapper1.ne("science_id",stujoin.getScienceId());
            }
            IPage<Science> scienceIPage=new Page<>(page,limit);
            IPage<Science> result=scienceMapper.selectPage(scienceIPage,wrapper1);
            List<Science> scienceList=result.getRecords();

            tableVo.setCount(scienceMapper.selectCount(wrapper1));
            tableVo.setData(scienceList);
        }else{

        }
        return tableVo;
    }

    @Override
    public int attend(int  scienceId, HttpSession session) {
        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        Integer studentId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);
        Stujoin stujoin=new Stujoin();
        stujoin.setScienceId(scienceId);
        stujoin.setStudentId(studentId);
        List<Stujoin> stujoinList=stujoinMapper.selectList(null);
        stujoin.setSjId(stujoinList.get(stujoinList.size()-1).getSjId()+1);
        return stujoinMapper.insert(stujoin);
    }

    @Override
    public TableVo<Science> getMySciences(HttpSession session) {
        TableVo tableVo=new TableVo();
        tableVo.setCode(0);
        tableVo.setMsg("");

        LoginInformation loginInformation=(LoginInformation) session.getAttribute("student");
        Integer studentId=loginInformation.getRelativeId();
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("student_id",studentId);

        QueryWrapper wrapper1=new QueryWrapper();
        if(stujoinMapper.selectList(wrapper).size()!=0){
            List<Stujoin> stujoinList=stujoinMapper.selectList(wrapper);
            for(Stujoin stujoin:stujoinList){
                wrapper1.eq("science_id",stujoin.getScienceId());
                wrapper1.or();
            }
            List<Science> scienceList=scienceMapper.selectList(wrapper1);
            tableVo.setCount(scienceList.size());
            tableVo.setData(scienceList);
        }else{

        }
        return tableVo;
    }
}