package com.upc.eden.bank.controller;

import com.upc.eden.bank.service.DifficultyService;
import com.upc.eden.commen.domain.bank.Difficulty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/03/18/17:24
 * @Description:
 */

@Api(tags = { "题目难度增删改查接口文档"} )
@RestController
@RequestMapping("/difficulty")

public class DifficultyController {

    @Resource
    DifficultyService difficultyService;

    @GetMapping
    @ApiOperation("获取全部题目难度的Map")
    public List<Difficulty> list() {

        List<Difficulty> difficultyList = difficultyService.list();
        return difficultyList;
    }
}
