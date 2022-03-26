package com.upc.eden.bank.controller;

import com.upc.eden.bank.service.SubjectService;
import com.upc.eden.commen.domain.bank.Subject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

@Api(tags = { "科目增删改查接口文档"} )
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Resource
    SubjectService subjectService;

    @GetMapping
    @ApiOperation("获取全部科目的Map")
    public List<Subject> list() {

        List<Subject> subjectList = subjectService.list();
        return subjectList;
    }
}
