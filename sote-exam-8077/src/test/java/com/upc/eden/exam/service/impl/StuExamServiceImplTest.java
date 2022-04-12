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
        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", 1);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);

        // 1、界定考试是否截止，未结束无法批卷
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String deadline = df.format(new Date());
        System.out.println(deadline);
    }
}