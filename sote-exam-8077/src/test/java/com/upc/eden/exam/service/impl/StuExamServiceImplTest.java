package com.upc.eden.exam.service.impl;

import com.upc.eden.exam.api.findAllExamOfStuApi;
import com.upc.eden.exam.service.StuExamService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: CS Dong
 * @Date: 2022/04/11/11:16
 * @Description:
 */
@SpringBootTest
class StuExamServiceImplTest {

    @Resource
    StuExamService stuExamService;

    @Test
    void findAllExamOfStu() {
        List<findAllExamOfStuApi> allExamOfStu = stuExamService.findAllExamOfStu(1904011106);
        System.out.println(allExamOfStu);
    }
}