package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.commen.clients.AuthClient;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.service.StuExamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: CS Dong
 * @Date: 2022/04/05/19:57
 * @Description:
 */
@RestController
@RequestMapping("/exam")
public class StuExamController {

    @Resource
    private StuExamService stuExamService;
    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private AuthClient authClient;

    @GetMapping("/join/{word}")
    public String joinByWord(@PathVariable String word) {

        // 鉴别密钥是否有效
        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("word", word);
        ExamInfo exam = examInfoService.getOne(examInfoQueryWrapper);
        if(exam==null) return "密钥错误！";

        // 鉴别是否能加入考试
        SimpleDateFormat df = new SimpleDateFormat();
        String now = df.format(new Date());
        String startTime = df.format(exam.getStartTime());
        String deadLine = df.format(exam.getDeadline());
        int status;
        if(now.compareTo(startTime)<0)  status = 0;
//        else if(now.compareTo())
        return "";
    }
}
