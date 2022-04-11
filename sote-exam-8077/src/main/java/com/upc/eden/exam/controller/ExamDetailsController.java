package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.exam.service.ExamDetailService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/get/{details}")
    public List<ExamDetail> getDetails(@PathVariable Integer details) {

        QueryWrapper<ExamDetail> examDetailQueryWrapper = new QueryWrapper<>();
        examDetailQueryWrapper.eq("details", details)
                .orderBy(true, true, "ques_no");
        List<ExamDetail> res = examDetailService.list(examDetailQueryWrapper);
        return res;
    }
}
