package com.nsystem.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void test(){
        studentMapper.selectList(null).forEach(System.out::println);
    }

}