package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.service.ExamDetailService;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.service.StuExamService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @Author: CS Dong
 * @Date: 2022/04/12/15:08
 * @Description:
 */

@Api(tags = { "批阅业务接口文档" } )
@RestController
@RequestMapping("/mark")
public class MarkingController {

    @Resource
    private ExamDetailService examDetailService;
    @Resource
    private StuExamService stuExamService;
    @Resource
    private ExamInfoService examInfoService;

//    @GetMapping("/auto/{examId}")
//    public String autoMark(@PathVariable Integer examId) {
//
//        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
//        examInfoQueryWrapper.eq("exam_id", examId);
//        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);
//
//        // 1、界定考试是否截止，未结束无法批卷
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        String deadline = df.format(examInfo.getDeadline());
//        if (df.format(new Date()).compareTo(deadline) < 0) {
//            return "考试未结束，无法批卷，请在 " + deadline + " 后提交请求！";
//        }
//
//        // 2、若考生考试状态滞留为1，则将其考试状态强制置2
//        UpdateWrapper<StuExam> stuExamUpdateWrapper = new UpdateWrapper<>();
//        stuExamUpdateWrapper.eq("exam_id",examId);
//        stuExamUpdateWrapper.eq("status", 1);
//        stuExamUpdateWrapper.set("status", 2);
//        stuExamService.update(null, stuExamUpdateWrapper);
//
//        // 3、若考生考试状态为0，则将其考试状态置-1
//        stuExamUpdateWrapper.eq("status", 0);
//        stuExamUpdateWrapper.set("status", -1);
//        stuExamService.update(null, stuExamUpdateWrapper);
//
//        // 3、确认无误，执行自动批卷操作
//
//    }
}
