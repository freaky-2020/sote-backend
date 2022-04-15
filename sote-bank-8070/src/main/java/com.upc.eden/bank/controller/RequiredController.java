package com.upc.eden.bank.controller;

import com.upc.eden.bank.service.BankRequireService;
import com.upc.eden.commen.domain.bank.BankRequire;
import com.upc.eden.commen.domain.bank.Question;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: CS Dong
 * @Date: 2022/04/14/23:10
 * @Description:
 */
@Api(tags = { "题目变动申请与审批接口文档"} )
@RestController
@RequestMapping("/required")
public class RequiredController {

    @Resource
    private BankRequireService bankRequireService;

//    @GetMapping("/{userName}/add")
//    public String add(@PathVariable Integer userName, Question question) {
//
//        BankRequire bankRequire = new BankRequire(question);
//        bankRequire.setDoWay(1);
//        bankRequire.setRequestUserName(userName);
//        bankRequire.
//    }
}
