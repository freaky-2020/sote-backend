package com.upc.eden.bank.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.bank.service.QuestionService;
import com.upc.eden.commen.domain.bank.Difficulty;
import com.upc.eden.commen.domain.bank.Question;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/03/31/18:33
 * @Description:
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/getQuesByIds")
    public List<Question> getQuesByIds(@RequestParam List<Integer> qIds) {

        List<Question> questions = new ArrayList<>();

        for (int qId: qIds) {
            Question question = questionService.getById(qId);
            questions.add(question);
        }
        return questions;
    }

    @GetMapping("/getQuesById")
    public Question getQuesById(@RequestParam Integer qId) {

        Question question = questionService.getById(qId);
        return question;
    }

    @GetMapping("/getQuesByType")
    List<Question> getQuesByType(@RequestParam Integer typeId, @RequestParam Integer subjectId) {

        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("type_id", typeId);
        questionQueryWrapper.eq("subject_id", subjectId);
        List<Question> list = questionService.list(questionQueryWrapper);
        return list;
    }
}
