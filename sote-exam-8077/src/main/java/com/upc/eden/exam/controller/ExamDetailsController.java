package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.exam.service.ExamDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/04/11/12:02
 * @Description:
 */

@Api(tags = { "考生作答接口文档"} )
@RestController
@RequestMapping("/detail")
public class ExamDetailsController {

    @Resource
    private ExamDetailService examDetailService;

    @ApiOperation("根据details值获取考生作答明细")
    @ApiImplicitParams({@ApiImplicitParam(name = "details", value = "明细外键", paramType = "path")})
    @GetMapping("/get/{details}")
    public List<ExamDetail> getDetails(@PathVariable Integer details) {

        QueryWrapper<ExamDetail> examDetailQueryWrapper = new QueryWrapper<>();
        examDetailQueryWrapper.eq("details", details)
                .orderBy(true, true, "ques_no");
        List<ExamDetail> res = examDetailService.list(examDetailQueryWrapper);
        return res;
    }

    @ApiOperation("考生作答，精细到各题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "details", value = "作答明细外键", paramType = "path"),
            @ApiImplicitParam(name = "quesNo", value = "题号", paramType = "path"),
            @ApiImplicitParam(name = "answer", value = "回传考生答案")})
    @GetMapping("/answer/{details}/{quesNo}")
    public boolean answerQues(@PathVariable Integer details, @PathVariable Integer quesNo,
                              String answer) {

        UpdateWrapper<ExamDetail> examDetailUpdateWrapper = new UpdateWrapper<>();
        examDetailUpdateWrapper.eq("details", details);
        examDetailUpdateWrapper.eq("ques_no", quesNo);
        examDetailUpdateWrapper.set("answer", answer);
        boolean res = examDetailService.update(examDetailUpdateWrapper);

        return res;
    }
}
