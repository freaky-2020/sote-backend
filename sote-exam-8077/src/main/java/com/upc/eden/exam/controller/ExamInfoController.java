package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.clients.AuthClient;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.exam.api.ExamInfoApi;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.tool.RandomSecret;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: CS Dong
 * @Date: 2022/03/28/11:16
 * @Description:
 */

@Api(tags = { "考试信息业务接口文档"} )
@RestController
@RequestMapping("/info")
public class ExamInfoController {

    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private AuthClient authClient;
    @Resource
    private RandomSecret randomSecret;

    @PostMapping("/add")
    @ApiOperation("添加考试相关信息：返回本场考试唯一试卷Id与唯一密钥口令，详见Schemas")
    public ExamInfoApi add(ExamInfo examInfo) {

        Integer maxPaperId = examInfoService.findMaxPaperId();
        Integer paperId;
        if (maxPaperId == null) paperId = 1;
        else paperId = maxPaperId + 1;

        String secret;
        if (examInfo.getNoticeWay()==1) {
            QueryWrapper<ExamInfo> wrapper = new QueryWrapper<>();
            do{
                secret = randomSecret.getRandomSecret(16);
                wrapper.eq("word", secret);
            } while (examInfoService.getOne(wrapper) != null);
        }
        else secret = null;

        examInfo.setPaperId(paperId);
        examInfo.setWord(secret);
//        examInfo.setInvigilatorId(authClient.getCurrentUser().getId());
        examInfo.setInvigilatorId(3);

        examInfoService.save(examInfo);
        return new ExamInfoApi(paperId, secret);
    }

    @ApiOperation("修改考试相关信息：考试Id与参加考试方式不可修改")
    @PutMapping("/update")
    public boolean update(ExamInfo examInfo) {

        UpdateWrapper<ExamInfo> wrapper = new UpdateWrapper<>();
        examInfo.setNoticeWay(null);
        wrapper.eq("exam_id", examInfo.getExamId());
        boolean res = examInfoService.update(examInfo, wrapper);
        return res;
    }

    @ApiOperation("删除考试信息：examId拼接在路径尾")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试信息Id", paramType = "path")})
    @DeleteMapping("/delete/{examId}")
    public boolean delete(@PathVariable Integer examId) {

        QueryWrapper<ExamInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("exam_id", examId);
        boolean res = examInfoService.remove(wrapper);
        return res;
    }

    @ApiOperation("刷新某考试的密钥口令：examId拼接在路径尾")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试信息Id", paramType = "path")})
    @GetMapping("/refreshWord/{examId}")
    public boolean changeWord(@PathVariable Integer examId) {

        QueryWrapper<ExamInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(wrapper);
        if (examInfo != null && examInfo.getNoticeWay()==1) {
            String secret;
            QueryWrapper<ExamInfo> wrapper2 = new QueryWrapper<>();
            do{
                secret = randomSecret.getRandomSecret(16);
                wrapper2.eq("word", secret);
            } while (examInfoService.getOne(wrapper2) != null);
            examInfo.setWord(secret);
            return examInfoService.update(examInfo, wrapper);
        } else return false;
    }

    @ApiOperation("按照传入的若干条件检索对应的考试信息，返回一个list：" +
            "{ 0:还未开放的考试 1:进行中的考试 2:已截止的考试 3:已公布的考试 }，均按科目、开始时间排序")
    @GetMapping("/query")
    public List<List<ExamInfo>> query(ExamInfo examInfo) {

        QueryWrapper<ExamInfo> wrapper = new QueryWrapper<>(examInfo);
        wrapper.orderBy(true, true, "subject_id");
        wrapper.orderBy(true, true, "start_time");
        List<ExamInfo> examInfos = examInfoService.list(wrapper);

        List<List<ExamInfo>> res = new ArrayList<>();
        for(int i = 0; i < 4; i++) res.add(new ArrayList<>());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String date = df.format(new Date());
        for(ExamInfo record: examInfos) {
            if(record != null) {
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println(record.getStartTime());
                System.out.println();
                if (record.getIsPublic()==1) res.get(3).add(record);
                else if (date.compareTo(df.format(record.getStartTime()))<0) res.get(0).add(record);
                else if(date.compareTo(df.format(record.getDeadline()))>0) res.get(2).add(record);
                else res.get(1).add(record);
            }
        }
        return res;
    }
}
