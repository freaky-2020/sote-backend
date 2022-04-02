package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.exam.api.ExamInfoApi;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.tool.RandomSecret;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    private RandomSecret randomSecret;

    @PostMapping("/add")
    @ApiOperation("添加考试相关信息：返回本场考试唯一试卷Id与唯一密钥口令，详见Schemas")
    public ExamInfoApi add(@ModelAttribute ExamInfo examInfo) {

        Integer maxPaperId = examInfoService.findMaxPaperId();
        Integer paperId;
        if (maxPaperId == null) paperId = 1;
        else paperId = maxPaperId + 1;

        String secret;
        if (examInfo.getNoticeWay()==1) secret = randomSecret.getRandomSecret(16);
        else secret = null;

        examInfo.setPaperId(paperId);
        examInfo.setWord(secret);

        examInfoService.save(examInfo);
        return new ExamInfoApi(paperId, secret);
    }

    @ApiOperation("修改考试相关信息：考试Id与参加考试方式不可修改")
    @PutMapping("/update")
    public boolean update(@ModelAttribute ExamInfo examInfo) {

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
            examInfo.setWord(randomSecret.getRandomSecret(16));
            boolean res = examInfoService.update(examInfo, wrapper);
            return res;
        } return false;
    }
}
