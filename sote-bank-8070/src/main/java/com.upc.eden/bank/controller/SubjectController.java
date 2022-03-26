package com.upc.eden.bank.controller;

import com.upc.eden.bank.service.SubjectService;
import com.upc.eden.commen.domain.bank.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/03/18/17:25
 * @Description:
 */
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Resource
    SubjectService subjectService;

    @GetMapping
    public List<Subject> list() {

        List<Subject> subjectList = subjectService.list();
        return subjectList;
    }
}
