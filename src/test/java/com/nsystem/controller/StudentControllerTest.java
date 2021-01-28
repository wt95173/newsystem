package com.nsystem.controller;

import com.nsystem.service.StudentService;
import com.nsystem.vo.TableVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentControllerTest {
    @Autowired
    private StudentService studentService;

    @Test
    void test(){
        TableVo tableVo=studentService.getCourse(1,10);
        System.out.println(tableVo.getData());
    }
}