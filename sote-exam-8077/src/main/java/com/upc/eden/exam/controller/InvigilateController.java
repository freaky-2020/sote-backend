package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.service.StuExamService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author hdh
 * @date 2022/4/11 - 16:57
 */
@Api(tags = { "监考接口文档"} )
@RestController
@RequestMapping("/invi")
public class InvigilateController {
    @Resource
    private StuExamService stuExamService;

    @GetMapping("/getSc")
    public Integer getSc(Integer exam_id,Integer examinee_id,Integer present_time){
        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("exam_id",exam_id);
        stuExamQueryWrapper.eq("examinee_id",examinee_id);
        stuExamQueryWrapper.eq("present_time",present_time);
        StuExam one = stuExamService.getOne(stuExamQueryWrapper);

        return one.getCuttingTime();
    }

    @GetMapping("/updateSc")
    public boolean updateSc(Integer exam_id,Integer examinee_id,Integer present_time,Integer scNum){
        StuExam stuExam = new StuExam();
        stuExam.setCuttingTime(scNum);
        System.out.println(scNum);
        UpdateWrapper<StuExam> stuExamUpdateWrapper = new UpdateWrapper<>();
        stuExamUpdateWrapper.eq("exam_id",exam_id);
        stuExamUpdateWrapper.eq("examinee_id",examinee_id);
        stuExamUpdateWrapper.eq("present_time",present_time);
        return stuExamService.update(stuExam,stuExamUpdateWrapper);
    }
    @GetMapping("/updateCheat")
    public boolean updateCheat(Integer exam_id,Integer examinee_id,Integer present_time,Integer isCheat){
        StuExam stuExam = new StuExam();
        stuExam.setCheat(isCheat);
        UpdateWrapper<StuExam> stuExamUpdateWrapper = new UpdateWrapper<>();
        stuExamUpdateWrapper.eq("exam_id",exam_id);
        stuExamUpdateWrapper.eq("examinee_id",examinee_id);
        stuExamUpdateWrapper.eq("present_time",present_time);
        return stuExamService.update(stuExam, stuExamUpdateWrapper);
    }
}