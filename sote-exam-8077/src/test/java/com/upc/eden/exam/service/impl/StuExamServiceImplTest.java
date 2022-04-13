package com.upc.eden.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.exam.api.FindAllExamOfStuApi;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.service.StuExamService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @Author: CS Dong
 * @Date: 2022/04/11/11:16
 * @Description:
 */
@SpringBootTest
class StuExamServiceImplTest {

    @Resource
    StuExamService stuExamService;
    @Resource
    ExamInfoService examInfoService;

    @Test
    void findAllExamOfStu() {
        List<FindAllExamOfStuApi> allExamOfStu = stuExamService.findAllExamOfStu(1904011106);
        System.out.println(allExamOfStu);
    }

    @Test
    void sasd() {
        String aa = "1,2,3";
        String b = "3,2";
        List<String> ra = Arrays.asList(aa.split(","));
        List<String> a = Arrays.asList(b.split(","));

        System.out.println(ra.containsAll(a));
        System.out.println(a.containsAll(ra));
    }
}