package com.upc.eden.bank.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upc.eden.bank.service.QuestionService;
import com.upc.eden.commen.domain.bank.Question;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @Author: CS Dong
 * @Date: 2022/03/17/21:16
 * @Description:
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Resource
    QuestionService questionService;

    /**
     * 按题型获取题目
     * @param typeId 题型Id: {1:单选 2:多选 3:判断 4:填空 5:综合}
     * @param cp 当前页码，默认为1
     * @param size 每页条数，默认为10
     * @return 对应题型的题目页集，按科目、难度依次降序
     */
    @GetMapping("/type/{typeId}")
    public Page<Question> typeList(@PathVariable Integer typeId,
                                   @RequestParam(defaultValue = "1") long cp,
                                   @RequestParam(defaultValue = "10") long size) {

        Page<Question> page = new Page<>(cp, size);
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("type_id", typeId)
                .orderBy(true, true, "subject_id")
                .orderBy(true, true, "difficulty_id");
        Page<Question> typePage = questionService.page(page, questionQueryWrapper);
        return typePage;
    }

    /**
     * 按科目获取题目
     * @param subjectId 科目Id
     * @param cp 当前页码，默认为1
     * @param size 每页条数，默认为10
     * @return 对应科目的题目页集，按题型、难度依次降序
     */
    @GetMapping("/subject/{subjectId}")
    public Page<Question> subjectList(@PathVariable Integer subjectId,
                                      @RequestParam(defaultValue = "1") long cp,
                                      @RequestParam(defaultValue = "10") long size) {

        Page<Question> page = new Page<>(cp, size);
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("subject_id", subjectId)
                .orderBy(true, true, "type_id")
                .orderBy(true, true, "difficulty_id");
        Page<Question> subjectPage = questionService.page(page, questionQueryWrapper);
        return subjectPage;
    }

    /**
     * 按照传入的请求信息获取题目
     * @param question 传入的题目信息(如科目Id、题型Id)会被自动封装
     * @param startTime 题目创建时间的范围-起始时间: 格式为 yyyy-MM-dd HH:mm:ss
     * @param endTime 题目创建时间的范围-截至时间: 格式为 yyyy-MM-dd HH:mm:ss
     * @param cp cp 当前页码，默认为1
     * @param size size 每页条数，默认为10
     * @return 对应的题目页集，按科目、题型、难度依次降序
     */
    @PostMapping("/query")
    public Page<Question> query(Question question,
                                @RequestParam(required = false) String startTime,
                                @RequestParam(required = false) String endTime,
                                @RequestParam(defaultValue = "1") long cp,
                                @RequestParam(defaultValue = "10") long size) {

        Page<Question> page = new Page<>(cp, size);
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>(question);
        if(startTime != null) {
            questionQueryWrapper.ge("create_time", startTime);
        }
        if(endTime != null) {
            questionQueryWrapper.le("create_time", endTime);
        }
        questionQueryWrapper
                .orderBy(true, true, "subject_id")
                .orderBy(true, true, "type_id")
                .orderBy(true, true, "difficulty_id");
        Page<Question> questionPage = questionService.page(page, questionQueryWrapper);
        return questionPage;
    }
}
