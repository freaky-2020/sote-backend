package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.commen.clients.AuthClient;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.service.StuExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/04/05/19:57
 * @Description:
 */
@Api(tags = { "学生加入考试业务接口文档"} )
@RestController
@RequestMapping("/exam")
public class StuExamController {

    @Resource
    private StuExamService stuExamService;
    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private AuthClient authClient;

    @ApiOperation("依据考试密钥加入对应考试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "word", value = "密钥", paramType = "path"),
            @ApiImplicitParam(name = "examineeId", value = "考生userName")})
    @GetMapping("/join/{word}")
    public String joinByWord(@PathVariable String word, Integer examineeId) {

        // 鉴别密钥是否有效
        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("word", word);
        ExamInfo exam = examInfoService.getOne(examInfoQueryWrapper);
        if(exam==null) return "密钥错误！";

        Integer examId = exam.getExamId();

        // 判断是否重复加入
        QueryWrapper isInWrapper = new QueryWrapper<StuExam>();
        isInWrapper.eq("examinee_id", examineeId);
        isInWrapper.eq("exam_id", examId);
        if(stuExamService.list(isInWrapper).size() != 0) return "您已加入过该考试，请在您的考试中心查看！";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = df.format(new Date());
        String startTime = df.format(exam.getStartTime());
        String deadLine = df.format(exam.getDeadline());

        // 加入考试
        boolean res = true;
        for (int presentTime=1; presentTime<=exam.getAllowableTime(); presentTime++) {

            Integer maxDetail = stuExamService.findMaxDetail();
            Integer details = maxDetail == null ? 1 : maxDetail + 1;
            StuExam stuExam = new StuExam(examineeId, examId, details, presentTime);
            stuExam.setStatus(now.compareTo(deadLine) > 0 ? -1 : null);
            res = res && stuExamService.save(stuExam);
        }

        if(res) {
            if(now.compareTo(startTime)<0) return "加入考试成功！请留意考试开放时间，切勿错过考试!";
            else if(now.compareTo(deadLine)>0) return "加入考试成功！该考试已结束，您只能在试题开放后浏览试题内容！";
            else return "加入考试成功！考试已开放，请留意考试截止时间，切勿错过考试！";
        }
        return "加入考试失败，请稍后再试！";
    }
}
